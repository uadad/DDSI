/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Monitor;
import Modelo.MonitorDAO;
import Vista.InsertarMonitor;
import Vista.vMensaje;
import Vista.vMonitores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
public class ControladorMonitor implements ActionListener {

    private Session session;
    private Transaction transaction;
    private vMonitores vMonitor;
    private utilTablasMonitores uTablasM;
    private MonitorDAO monitordao;
    private SessionFactory sessionFactory;
    private vMensaje mensaje;
    private InsertarMonitor insertaM;

    public ControladorMonitor(vMonitores vMonitor, SessionFactory sessionFactory) {
        this.vMonitor = vMonitor;
        this.sessionFactory = sessionFactory;
        uTablasM = new utilTablasMonitores();
        insertaM = new InsertarMonitor();
        mensaje = new vMensaje();
        monitordao = new MonitorDAO();
        addListeners();
        dibujarTabla();

    }

    private void addListeners() {
        vMonitor.jButtonActualizarMonitor.addActionListener(this);
        vMonitor.jButtonBorrarMonitor.addActionListener(this);
        vMonitor.jButtonNuevoMonitor.addActionListener(this);

        insertaM.jButtonAceptarMonitor.addActionListener(this);
        insertaM.jButtonCancelarMonitor.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        // System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {

            case "Nuevo Monitor":
                VaciarDatos();

                session = sessionFactory.openSession();
                transaction = session.beginTransaction();

                try {
                    String[] s = monitordao.DevolverUltimoCodigo(session).split("M");
                    int numeroMonitor = Integer.parseInt(s[1]);
                    numeroMonitor++;
                    String codigoM = "M" + String.valueOf(numeroMonitor);
                    if (codigoM.length() == 2) {
                        codigoM = "M00" + numeroMonitor;
                    } else if (codigoM.length() == 3) {
                        codigoM = "M0" + numeroMonitor;
                    }
                    insertaM.CodigoMonitor.setText(codigoM);
                    insertaM.CodigoMonitor.setEditable(false);
                    insertaM.setLocationRelativeTo(null);
                    insertaM.setVisible(true);

                    transaction.commit();
                } catch (Exception ex) {
                    transaction.rollback();
                } finally {
                    if (session != null && session.isOpen()) {
                        session.close();
                    }
                }

                break;
            case "Baja de Monitor":
                int bajaM = vMonitor.jTableMonitores.getSelectedRow();
                if (bajaM != -1) {
                    int opc = mensaje.BajaDialog(vMonitor, "M");
                    if (opc == JOptionPane.YES_OPTION) {
                        session = sessionFactory.openSession();
                        transaction = session.beginTransaction();

                        try {
                            ArrayList<Monitor> lMonitores = pideMonitores();
                            System.out.println("BajaM: " + bajaM + "    CodSoc: " + lMonitores.get(bajaM).getCodmonitor());
                            monitordao.eliminaMonitor(session, lMonitores.get(bajaM));
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
                    mensaje.mensajeConsola("No se ha elegido Ningun Monitor.");
                }
                break;

            case "Actualizar un Monitor":
                int ActualizaM = vMonitor.jTableMonitores.getSelectedRow();
                if (ActualizaM != -1) {
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();

                    try {
                        ArrayList<Monitor> lMonitores = pideMonitores();
                        System.out.println("ActualizaM: " + ActualizaM + "    CodSoc: " + lMonitores.get(ActualizaM).getCodmonitor());
                        Monitor m = lMonitores.get(ActualizaM);
                        if (m != null) {
                            insertaM.CodigoMonitor.setText(m.getCodmonitor());
                            insertaM.CodigoMonitor.setEditable(false);
                            insertaM.CorreoMonitor.setText(m.getCorreo());
                            insertaM.DniMonitor.setText(m.getDni());
                            insertaM.NomMonitor.setText(m.getNombre());
                            insertaM.NickMonitor.setText(m.getNick());
                            insertaM.TelMonitor.setText(m.getTelefono());

                            String[] fechaent = m.getFechaentrada().split("/");
                            Date fechaent2 = new Date(Integer.parseInt(fechaent[2]) - 1900, Integer.parseInt(fechaent[1]) - 1, Integer.parseInt(fechaent[0]));

                            insertaM.FechaNacMonitor.setDate(fechaent2);
                            insertaM.setLocationRelativeTo(null);
                            insertaM.setVisible(true);

                        } else {
                            mensaje.mensajeConsola("No se encuetra dicho Monitor.");
                        }

                        transaction.commit();
                    } catch (Exception ex) {
                        transaction.rollback();
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }

                } else {
                    mensaje.mensajeConsola("No se ha elegido Ningun Monitor.");
                }
                break;

            case "AceptarMonitor":

                session = sessionFactory.openSession();
                transaction = session.beginTransaction();

                try {
                    String[] datos = new String[7];
                    datos[0] = insertaM.CodigoMonitor.getText();
                    datos[1] = insertaM.NomMonitor.getText();
                    datos[2] = insertaM.DniMonitor.getText();
                    Pattern patron = Pattern.compile("[0-9]{8}[A-Z]");
                    Matcher mat = patron.matcher(datos[2]);
                    if (!mat.matches()) {
                        mensaje.mensajeConsola("El campo dni debe de tener 8 digitos y una letra mayuscula.");
                    }
                    datos[2] = insertaM.DniMonitor.getText();
                    datos[3] = insertaM.TelMonitor.getText();
                    Pattern patron2 = Pattern.compile("[0-9]{9}");
                    Matcher mat2 = patron2.matcher(datos[3]);
                    boolean valido_tel = true;
                    if (!datos[3].equals("")) {
                        if (!mat2.matches()) {
                            mensaje.mensajeConsola("El campo telefono debe de tener 9 digitos.");
                            valido_tel = false;
                        }
                    }
                    datos[4] = insertaM.CorreoMonitor.getText();
                     patron2 = Pattern.compile("[a-zA-Z0-9_.]{3,20}@[a-zA-Z0-9.]{3,20}");
                     mat2 = patron2.matcher(datos[4]);
                    boolean valido_correo = true;
                    if (!datos[4].equals("")) {
                        if (!mat2.matches()) {
                            mensaje.mensajeConsola("El campo correo debe de tener al menos 3 letras y una @.");
                            valido_correo = false;
                        }
                    }

                    datos[6] = insertaM.NickMonitor.getText();
                    if (insertaM.FechaNacMonitor.getDate() != null) {
                        datos[5] = Establecerfecha(insertaM.FechaNacMonitor.getDate().toString());
                    } else {
                        datos[5] = "";
                    }

                    //Comprobamos que los campos obligatorios estan puestos
                    if ((!datos[1].isEmpty() && !datos[2].isEmpty() && !datos[5].isEmpty()) && !insertaM.FechaNacMonitor.getDate().after(new Date(System.currentTimeMillis())) && mat.matches() && valido_correo && valido_tel) {
                        Monitor m = new Monitor(datos[0], datos[1], datos[2], datos[5]); //creamos el monitor con los campos obligatorios
                        if (!datos[3].isEmpty()) {
                            m.setTelefono(datos[3]);
                        }
                        if (!datos[4].isEmpty()) {
                            m.setCorreo(datos[4]);
                        }
                        if (!datos[6].isEmpty()) {
                            m.setNick(datos[6]);
                        }

                        System.out.println(m.toString());
                        monitordao.insertaActualizaMonitor(session, m);
                    } else {
                        mensaje.mensajeConsola("Debe rellenar todos los campos obligatorios Correctamente.");
                    }
                    transaction.commit();
                } catch (Exception ex) {
                    transaction.rollback();
                } finally {
                    if (session != null && session.isOpen()) {
                        session.close();
                    }
                    insertaM.dispose();
                    dibujarTabla();
                    VaciarDatos();

                }

                break;

            case "CancelarMonitor":
                insertaM.dispose();
                VaciarDatos();
                break;

        }

    }

    private void dibujarTabla() {

        uTablasM.dibujarTablaMonitores(vMonitor);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            ArrayList<Monitor> listaMonitores = pideMonitores();
            uTablasM.vaciarTablaMonitores();
            uTablasM.rellenarTablaMonitores(listaMonitores);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            mensaje.mensajeConsola("Error en la petición de Monitores" + ex.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    private ArrayList<Monitor> pideMonitores() throws Exception {
        ArrayList<Monitor> lMonitores = monitordao.listaMonitoresPorCodigo(session);
        return lMonitores;
    }

    private void VaciarDatos() {
        insertaM.NomMonitor.setText("");
        insertaM.TelMonitor.setText("");
        insertaM.CorreoMonitor.setText("");
        insertaM.DniMonitor.setText("");
        insertaM.FechaNacMonitor.setDate(null);
        insertaM.NickMonitor.setText("");
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
