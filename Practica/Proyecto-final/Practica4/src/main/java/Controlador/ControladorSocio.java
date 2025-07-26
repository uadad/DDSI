/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Excepciones;
import Modelo.Socio;
import Modelo.SocioDAO;
import Vista.InsertarSocio;
import Vista.vMensaje;
import Vista.vSocios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
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
    private ControladorVistaSocio controladorVistaActividad;
    private Excepciones excep = new Excepciones();

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

    private void addListeneres() {
        insertaS.jButtonAceptarSocio.addActionListener(this);
        insertaS.jButtonCancelarSocio.addActionListener(this);

        vSocio.jButtonActualizarSocio.addActionListener(this);
        vSocio.jButtonBorrarSocio.addActionListener(this);
        vSocio.jButtonNuevoSocio.addActionListener(this);
        vSocio.jButtonGestionarActividades.addActionListener(this);
        vSocio.FiltrarSocio.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Nuevo Socio":
                session = sessionFactory.openSession();
                transaction = session.beginTransaction();
                try {

                    String numSocio = calcular_Socio();
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
                        transaction.commit();
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

                String[] datos = new String[8];
                datos[0] = insertaS.CodigoSocio.getText();
                datos[1] = insertaS.NomSocio.getText();
                datos[2] = insertaS.DniSocio.getText();
                datos[3] = insertaS.TelSocio.getText();

                datos[4] = insertaS.CorreoSocio.getText();
                datos[7] = (String) insertaS.CatSocio.getSelectedItem();
                if (insertaS.FechaNacimientoSocio.getDate() != null) {
                    datos[5] = excep.Establecerfecha(insertaS.FechaNacimientoSocio.getDate().toString());
                } else {
                    datos[5] = "";
                }
                if (insertaS.FechaEntradaSocio.getDate() != null) {
                    datos[6] = excep.Establecerfecha(insertaS.FechaEntradaSocio.getDate().toString());
                } else {
                    datos[6] = "";
                }
               

                if (datos[1].isEmpty() || datos[2].isEmpty() || datos[6].isEmpty() || datos[7].isEmpty()) {
                    mensaje.mensajeConsola("Debe rellenar todos los campos obligatorios Correctamente.");
                } else {
                    try {

                        excep.comprobar_DNI(datos[2]);
                        excep.comprobar_Fecha(insertaS.FechaEntradaSocio.getDate());
                        if (!datos[3].equals("")) {
                            excep.comprobar_Telefono(datos[3]);
                        }
                        if (!datos[4].equals("")) {
                            excep.comprobar_Correo(datos[4]);
                        }
                        if (!datos[5].equals("")) {
                            excep.comprobar_Mayor18(datos[5]);
                        }
                    } catch (Exception ex) {
                        mensaje.mensajeConsola(ex.getMessage());
                        break;
                    }
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
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    try {
                        sociodao.insertaActualizaSocio(session, socioNuevo);
                        transaction.commit();
                    } catch (Exception ev) {
                        transaction.rollback();
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                    insertaS.dispose();
                    dibujarTabla();
                    VaciarDatos();
                }
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
            case "FiltrarSocio":
                String filtrado = (String) vSocio.FiltrarSocio.getSelectedItem();
                Character cat = filtrado.charAt(0);
                if (cat == '-') {
                    dibujarTabla();
                } else {
                    dibujarTabla(cat);
                }
                break;
        }

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

    private void dibujarTabla(Character c) {
        uTablasS.dibujarTablaSocios(vSocio);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        try {
            ArrayList<Socio> listaSocios = sociodao.devolverSociosCat(session, c);
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

    private void VaciarDatos() {
        insertaS.DniSocio.setText("");
        insertaS.CorreoSocio.setText("");
        insertaS.FechaEntradaSocio.setDate(null);
        insertaS.FechaNacimientoSocio.setDate(null);
        insertaS.NomSocio.setText("");
        insertaS.TelSocio.setText("");
        insertaS.CatSocio.setSelectedIndex(0);
    }

    private String calcular_Socio() {
        String numS = sociodao.DevolverUltimoSocio(session);
        String[] aux = numS.split("S");
        int num = Integer.parseInt(aux[1]);
        num++;
        String numSocio = String.valueOf(num);
        if (numSocio.length() == 2) {
            numSocio = "S0" + String.valueOf(num);
        } else if (numSocio.length() == 1) {
            numSocio = "S00" + String.valueOf(num);
        }
        return numSocio;
    }

}
