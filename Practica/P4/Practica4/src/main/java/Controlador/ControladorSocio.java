/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import Modelo.SocioDAO;
import Vista.InsertarSocio;
import Vista.vMensaje;
import Vista.vSocios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author wadad
 */
public class ControladorSocio implements ActionListener {

    private Session session;
    private Transaction transaction;
    private vMensaje mensaje;
    private SessionFactory sessionFactory;
    private utilTablasSocio uTablasS;
    private InsertarSocio insertaS;
    private SocioDAO sociodao;
    private vSocios vSocio;
    private String numSocio;
    private ControladorVistaSocio controladorVistaActividad;

    public ControladorSocio(SessionFactory sessionFactory, vSocios vSocio) {
        this.sessionFactory = sessionFactory;
        this.vSocio = vSocio;

        mensaje = new vMensaje();
        sociodao = new SocioDAO();

        uTablasS = new utilTablasSocio();
        insertaS = new InsertarSocio();

        addListeneres();

        dibujarTabla();

    }

    private void dibujarTabla() {
        uTablasS.dibujarTablaSocios(vSocio);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        try {
            ArrayList<Socio> listaSocios = pideSocios();
            uTablasS.vaciarTablaSocios();
            uTablasS.rellenarTablaSocios(listaSocios);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            mensaje.mensajeConsola("Error en la petición de Socios" + ex.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    private ArrayList<Socio> pideSocios() throws Exception {
        ArrayList<Socio> listaSocios = sociodao.listaSociosPorNumero(session);
        return listaSocios;
    }

    private void addListeneres() {
        insertaS.jButtonAceptarSocio.addActionListener(this);
        insertaS.jButtonCancelarSocio.addActionListener(this);

        vSocio.jButtonActualizarSocio.addActionListener(this);
        vSocio.jButtonBorrarSocio.addActionListener(this);
        vSocio.jButtonNuevoSocio.addActionListener(this);
        vSocio.jButtonGestionarActividades.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Nuevo Socio":
                session = sessionFactory.openSession();
                transaction = session.beginTransaction();
                try {

                    String numS = sociodao.DevolverUltimoSocio(session);
                    String[] aux = numS.split("S");
                    int num = Integer.parseInt(aux[1]);
                    num++;
                    numSocio = String.valueOf(num);
                    if (numSocio.length() == 2) {
                        numSocio = "S0" + String.valueOf(num);
                    } else if (numSocio.length() == 1) {
                        numSocio = "S00" + String.valueOf(num);
                    }
                    insertaS.CodigoSocio.setText(numSocio);
                    insertaS.CodigoSocio.setEditable(false);
                    insertaS.setLocationRelativeTo(null);
                    insertaS.setVisible(true);
                    transaction.commit();
                } catch (NumberFormatException ev) {
                    transaction.rollback();
                } finally {
                    if (session != null && session.isOpen()) {
                        session.close();
                    }
                }
                break;

            case "Baja de Socio":
                int bajaS = vSocio.modeloTablaSocios.getSelectedRow();
                if (bajaS != -1) {
                    int opc = mensaje.BajaDialog(vSocio, "Desea dar de baja a dicho Socio ?");
                    if (opc == JOptionPane.YES_OPTION) {
                        session = sessionFactory.openSession();
                        transaction = session.beginTransaction();

                        try {
                            sociodao.eliminaSocio(session, (String) vSocio.modeloTablaSocios.getValueAt(bajaS, 0));
                            transaction.commit();
                        } catch (Exception ex) {
                            transaction.rollback();
                        } finally {
                            if (session != null && session.isOpen()) {
                                session.close();
                            }
                        }
                        dibujarTabla();
                    }
                } else {
                    mensaje.mensajeConsola("No se ha elegido Ningun Socio.");
                }
                break;
            case "Actualizar un Socio":
                int actualiza = vSocio.modeloTablaSocios.getSelectedRow();
                if (actualiza != -1) {

                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();

                    try {

                        Socio s = session.get(Socio.class, (String) vSocio.modeloTablaSocios.getValueAt(actualiza, 0));

                        insertaS.CodigoSocio.setText(s.getNumerosocio());
                        insertaS.NomSocio.setText(s.getNombre());
                        insertaS.DniSocio.setText(s.getDni());
                        insertaS.TelSocio.setText(s.getTelefono());
                        insertaS.CorreoSocio.setText(s.getCorreo());

                        char cat = s.getCategoria();
                        switch (cat) {
                            case 'A':
                                insertaS.CatSocio.setSelectedIndex(0);
                                break;
                            case 'B':
                                insertaS.CatSocio.setSelectedIndex(1);
                                break;
                            case 'C':
                                insertaS.CatSocio.setSelectedIndex(2);
                                break;
                            case 'D':
                                insertaS.CatSocio.setSelectedIndex(3);
                                break;
                            case 'E':
                                insertaS.CatSocio.setSelectedIndex(4);
                                break;
                            default:
                                break;
                        }
                        String[] fechaent = s.getFechaentrada().split("/");
                        Date fechaent2 = new Date(Integer.parseInt(fechaent[2]) - 1900, Integer.parseInt(fechaent[1]) - 1, Integer.parseInt(fechaent[0]));
                        insertaS.FechaEntradaSocio.setDate(fechaent2);

                        fechaent = s.getFechanacimiento().split("/");
                        fechaent2 = new Date(Integer.parseInt(fechaent[2]) - 1900, Integer.parseInt(fechaent[1]) - 1, Integer.parseInt(fechaent[0]));
                        insertaS.FechaNacimientoSocio.setDate(fechaent2);

                        insertaS.CodigoSocio.setEditable(false);
                        insertaS.setLocationRelativeTo(null);
                        insertaS.setVisible(true);
                    } catch (NumberFormatException ev) {
                        transaction.rollback();
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                } else {
                    mensaje.mensajeConsola("No se ha elegido Ningun Socio.");
                }
                break;
            case "AceptarSocio":
                session = sessionFactory.openSession();
                transaction = session.beginTransaction();
                try {
                    String[] datos = new String[8];
                    datos[0] = insertaS.CodigoSocio.getText();
                    datos[1] = insertaS.NomSocio.getText();
                    datos[2] = insertaS.DniSocio.getText();
                    Pattern patron = Pattern.compile("[0-9]{8}[A-Z]");
                    Matcher mat = patron.matcher(datos[2]);
                    if (!mat.matches()) {
                        mensaje.mensajeConsola("El campo dni debe de tener 8 digitos y una letra mayuscula.");
                    }
                    datos[3] = insertaS.TelSocio.getText();
                    Pattern patron2 = Pattern.compile("[0-9]{9}");
                    Matcher mat2 = patron2.matcher(datos[3]);
                    boolean valido_tel = true;
                    if (!datos[3].equals("")) {
                        if (!mat2.matches()) {
                            mensaje.mensajeConsola("El campo telefono debe de tener 9 digitos.");
                            valido_tel = false;
                        }
                    }
                    datos[4] = insertaS.CorreoSocio.getText();
                    patron2 = Pattern.compile("[a-zA-Z0-9_.]{3,20}@[a-zA-Z0-9.]{3,20}");
                    mat2 = patron2.matcher(datos[4]);
                    boolean valido_correo = true;
                    if (!datos[4].equals("")) {
                        if (!mat2.matches()) {
                            mensaje.mensajeConsola("El campo correo debe de tener al menos 3 letras y una @.");
                            valido_correo = false;
                        }
                    }
                    datos[7] = (String) insertaS.CatSocio.getSelectedItem();
                    boolean edad = true;
                    if (insertaS.FechaNacimientoSocio.getDate() != null) {
                        datos[5] = Establecerfecha(insertaS.FechaNacimientoSocio.getDate().toString());
                        String[] mayor = datos[5].split("/");
                        int año_nac = Integer.parseInt(mayor[2]);
                        Calendar calendar = Calendar.getInstance();

                        calendar.setTime(new Date());
                        int año_actual = calendar.get(Calendar.YEAR);
                        if (año_actual - año_nac < 18) {
                            edad = false;
                            mensaje.mensajeConsola("EL socio debe tener al menos 18 años.");
                        }
                    } else {
                        datos[5] = "";
                    }
                    if (insertaS.FechaEntradaSocio.getDate() != null) {
                        datos[6] = Establecerfecha(insertaS.FechaEntradaSocio.getDate().toString());
                    } else {
                        datos[6] = "";
                    }
                    Date date = insertaS.FechaNacimientoSocio.getDate();

                    if ((!datos[1].isEmpty() && !datos[2].isEmpty() && !datos[6].isEmpty() && !datos[7].isEmpty() && mat.matches() && valido_tel && valido_correo && edad)
                            && !insertaS.FechaEntradaSocio.getDate().after(new Date(System.currentTimeMillis()))) {
                        if (date == null || !date.after(insertaS.FechaEntradaSocio.getDate())) {
                            Socio socioNuevo = new Socio(datos[0], datos[1], datos[2], datos[6], datos[7].charAt(0));

                            if (!datos[3].isEmpty()) {
                                socioNuevo.setTelefono(datos[3]);
                            }
                            if (!datos[4].isEmpty()) {
                                socioNuevo.setCorreo(datos[4]);
                            }
                            if (!datos[5].isEmpty()) {
                                socioNuevo.setFechanacimiento(datos[5]);
                            }
                            sociodao.insertaActualizaSocio(session, socioNuevo);
                        }
                    } else {
                        mensaje.mensajeConsola("Debe rellenar todos los campos obligatorios Correctamente.");
                    }
                    transaction.commit();
                } catch (Exception ev) {
                    transaction.rollback();
                    System.out.println("Exp");
                } finally {
                    if (session != null && session.isOpen()) {
                        session.close();
                    }
                }

                insertaS.dispose();
                dibujarTabla();
                VaciarDatos();
                break;
            case "CancelarSocio":
                insertaS.dispose();
                VaciarDatos();
                break;

            case "Gestionar Actividades":
                actualiza = vSocio.modeloTablaSocios.getSelectedRow();
                if (actualiza != -1) {
                    Socio socio = null;
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();

                    try {
                        String so = (String) vSocio.modeloTablaSocios.getValueAt(actualiza, 0);
                        socio = session.get(Socio.class, so);

                        transaction.commit();
                    } catch (Exception ev) {
                        transaction.rollback();
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                    controladorVistaActividad = new ControladorVistaSocio(sessionFactory, socio);
                } else {
                    mensaje.mensajeConsola("No se ha elegido Ningun Socio.");
                }
                break;
        }
    }

    private void VaciarDatos() {
        insertaS.DniSocio.setText("");
        insertaS.CorreoSocio.setText("");
        insertaS.FechaEntradaSocio.setDate(null);
        insertaS.FechaNacimientoSocio.setDate(null);
        insertaS.NomSocio.setText("");
        insertaS.TelSocio.setText("");
        insertaS.CatSocio.setSelectedIndex(0);
    }

    private String Establecerfecha(String s) {
        String[] aux = s.split(" ");

        String e = aux[2] + "/"; //Dia
        switch (aux[1]) {
            case "Jan":
                e += "01";
                break;
            case "Feb":
                e += "02";
                break;
            case "Mar":
                e += "03";
                break;
            case "Apr":
                e += "04";
                break;
            case "May":
                e += "05";
                break;
            case "Jun":
                e += "06";
                break;
            case "Jul":
                e += "07";
                break;
            case "Aug":
                e += "08";
                break;
            case "Sep":
                e += "09";
                break;
            case "Oct":
                e += "10";
                break;
            case "Nov":
                e += "11";
                break;
            case "Dec":
                e += "12";
                break;

        }
        e += "/" + aux[5];
        return e;
    }
}
