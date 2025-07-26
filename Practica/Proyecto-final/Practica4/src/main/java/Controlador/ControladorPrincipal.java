/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.VistaPrincipal;
import Vista.vActividades;
import Vista.vCategorias;
import Vista.vCuotaActividad;
import Vista.vCuotaSocio;
import Vista.vMonitores;
import Vista.vPrecioActividad;
import Vista.vSocios;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author wadad
 */
public class ControladorPrincipal implements ActionListener {

    private Session session;
    private SessionFactory sessionFactory;
    private Transaction transaction;
    private VistaPrincipal vistaprincipal;
    private vMonitores vMonitor;
    private vSocios vSocio;
    private vPrecioActividad vActPrecio;
    private JPanel pPrincipal;
    private ControladorSocio controladorsocio;
    private ControladorMonitor controladormonitor;
    private ControladorActividades controladorActividad;
    private vCuotaSocio vCuota;
    private vCategorias vCategoria;
    private ControladorExamen controladorExamen;
    private vCuotaActividad vCuotaActividad;

    public ControladorPrincipal(SessionFactory sessionFactory) throws Exception {

        vistaprincipal = new VistaPrincipal();
        vSocio = new vSocios();
        vMonitor = new vMonitores();
        vActPrecio = new vPrecioActividad();
        vCuota = new vCuotaSocio();
        vCategoria=new vCategorias();
        vCuotaActividad=new vCuotaActividad();
        
        pPrincipal = vistaprincipal.PanelPrincipal;

        controladorsocio = new ControladorSocio(sessionFactory, vSocio);
        controladormonitor = new ControladorMonitor(vMonitor, sessionFactory);
        controladorExamen=new ControladorExamen(sessionFactory,vCategoria,vCuotaActividad);
        this.sessionFactory = sessionFactory;

        addListeners();

        vistaprincipal.getContentPane().setLayout(new CardLayout());
        vistaprincipal.add(pPrincipal);
        vistaprincipal.add(vMonitor);
        vistaprincipal.add(vSocio);
        intercambiarPanel("pPrincipal");

        vistaprincipal.setLocationRelativeTo(null);
        vistaprincipal.setVisible(true);

    }

    private void addListeners() {
        vistaprincipal.GestionMonitores.addActionListener(this);
        vistaprincipal.SalirAplicacion.addActionListener(this);
        vistaprincipal.GestionSocios.addActionListener(this);
        vistaprincipal.Volver_Atras.addActionListener(this);
        vistaprincipal.Actualizacion_Precio.addActionListener(this);
        vistaprincipal.CuotaSocio.addActionListener(this);
        vistaprincipal.Actualizacion_Categoria.addActionListener(this);
        vistaprincipal.Cuota_Actividad.addActionListener(this);
    }

    private void intercambiarPanel(String p) {
        switch (p) {
            case "pPrincipal":
                pPrincipal.setVisible(true);
                vSocio.setVisible(false);
                vMonitor.setVisible(false);
                break;
            case "pSocio":
                pPrincipal.setVisible(false);
                vSocio.setVisible(true);
                vMonitor.setVisible(false);
                break;
            case "pMonitor":
                pPrincipal.setVisible(false);
                vSocio.setVisible(false);
                vMonitor.setVisible(true);
                break;
        }
    }

    public void actionPerformed(ActionEvent e) {

        //System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {

            case "Salir de la aplicación":
                vistaprincipal.dispose();
                System.exit(0);
                break;
            case "Gestion de Monitores":
                intercambiarPanel("pMonitor");
                break;
            case "Gestion de Socios":
                intercambiarPanel("pSocio");
                break;
            case "Actualización de Precio":
                controladorActividad = new ControladorActividades(sessionFactory, vActPrecio, vCuota);
                vActPrecio.setLocationRelativeTo(null);
                vActPrecio.setVisible(true);
                break;
            case "Volver Atras":
                intercambiarPanel("pPrincipal");
                break;
            case "Couta de Socios":
                controladorActividad = new ControladorActividades(sessionFactory, vActPrecio, vCuota);
                vCuota.setLocationRelativeTo(null);
                vCuota.setVisible(true);
                break;
            case "Actualización de Categorias":
                 vCategoria.setLocationRelativeTo(null);
                 vCategoria.setVisible(true);
                break;
            case "Cuota de Actividad":
                 vCuotaActividad.setLocationRelativeTo(null);
                 vCuotaActividad.setVisible(true);
                break;
        }

    }

}

/* private void practica3(SessionFactory sessionFactory) {
        int opc;
        vMenu vmenu = new vMenu();
        vMensaje mensaje = new vMensaje();
        vSocio vSocios = new vSocio();
        vMonitor vMonitor = new vMonitor();
        SocioDAO socioDAO = new SocioDAO();
        ActividadDAO actividaddao = new ActividadDAO();
        Scanner sc = new Scanner(System.in);
        do {
            vmenu.muestraMenu();
            opc = sc.nextInt();

            switch (opc) {
                case 1:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();

                    try {
                        ArrayList<Socio> listaSocios = socioDAO.listaSociosHQL(session);
                        vSocios.muestraSocios(listaSocios);
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la petición de socios", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }

                    break;
                case 2:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();

                    try {
                        ArrayList<Socio> listaSocios = socioDAO.listaSociosSQLNativo(session);
                        vSocios.muestraSocios(listaSocios);
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la petición de socios", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }

                    break;
                case 3:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();

                    try {
                        ArrayList<Socio> listaSocios = socioDAO.listaSociosConsultaNombrada(session);
                        vSocios.muestraSocios(listaSocios);
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la petición de socios", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }

                    break;
                case 4:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();

                    try {
                        ArrayList<Object[]> listaSocios = socioDAO.ListarNombreyTelefono(session);
                        vSocios.mostrarNombreyTelefono(listaSocios);
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la petición de socios", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }

                    break;
                case 5:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    sc.nextLine();
                    mensaje.mensajeConsola("Introduce la Categoria: ");
                    String cat = sc.nextLine();
                    try {
                        ArrayList<Object[]> listaSocios = socioDAO.ListarNombreyCategoria(session, cat.charAt(0));
                        vSocios.mostrarNombreyCategoria(listaSocios);
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la petición de socios", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }

                    break;
                case 6:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    sc.nextLine();
                    mensaje.mensajeConsola("Introduce la Actividad: ");
                    String act = sc.nextLine();
                    try {
                        Monitor monitor = actividaddao.ListarResponsables(session, act);
                        vMonitor.muestraMonitores(monitor);
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la petición de monitores", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }

                    break;
                case 7:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    sc.nextLine();
                    mensaje.mensajeConsola("Introduce la Actividad: ");
                    act = sc.nextLine();
                    try {
                        ArrayList<Socio> listaSocios = actividaddao.ListarSocios(session, act);
                        vSocios.muestraSocios(listaSocios);
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la petición de Actividades", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                    break;
                case 8:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    Socio socio = new Socio("S013", "Felipe jjjjjj", "76yyyyk", "05/11/2023", 'D');
                    try {

                        socioDAO.insertaActualizaSocio(session, socio);

                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la Insercion de socios", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                    break;

                case 9:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    sc.nextLine();
                    mensaje.mensajeConsola("Introduce el numero de socio que quieres borrar: ");
                    act = sc.nextLine();
                    try {
                        Socio s = session.get(Socio.class, act);
                        if (s == null) {
                            mensaje.mensajeConsola("Socio no existe en la BD");
                        } else {
                            socioDAO.eliminaSocio(session, s);
                        }
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la baja de un socio", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                    break;

                case 10:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    sc.nextLine();
                    mensaje.mensajeConsola("Introduce el numero de socio: ");
                    act = sc.nextLine();

                    try {
                        Socio s = session.get(Socio.class, act);
                        if (s == null) {
                            mensaje.mensajeConsola("Socio no existe en la BD");
                        } else {
                            mensaje.mensajeConsola("Introduce la categoria del socio: ");
                            char act2 = sc.nextLine().charAt(0);
                            s.setCategoria(act2);
                            socioDAO.insertaActualizaSocio(session, s);
                        }
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la actualizacion de un socio", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                    break;
                case 11:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    sc.nextLine();

                    mensaje.mensajeConsola("Introduce la Actividad: ");
                    act = sc.nextLine();
                    mensaje.mensajeConsola("Introduce el numero de socio: ");
                    String soc = sc.nextLine();
                    try {
                        Socio s = session.get(Socio.class, soc);
                        if (s != null) {
                            Actividad actividad = session.get(Actividad.class, act);
                            if (actividad != null) {
                                Set<Socio> socioset = actividad.getSocioSet();
                                socioset.add(s);
                                actividad.setSocioSet(socioset);
                                actividaddao.insertaActualizaActividad(session, actividad);
                            } else {
                                mensaje.mensajeConsola("no existe dicha actividad.");
                            }
                        } else {
                            mensaje.mensajeConsola("Socio no existe en la BD");

                        }
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la Alta de un socio de una actividad", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }

                    break;
                case 12:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    sc.nextLine();

                    mensaje.mensajeConsola("Introduce la Actividad: ");
                    act = sc.nextLine();
                    mensaje.mensajeConsola("Introduce el numero de socio: ");
                    soc = sc.nextLine();
                    try {
                        Socio s = session.get(Socio.class, soc);
                        if (s != null) {
                            Actividad actividad = session.get(Actividad.class, act);
                            if (actividad != null) {
                                Set<Socio> socioset = actividad.getSocioSet();
                                socioset.remove(s);
                                actividad.setSocioSet(socioset);
                                actividaddao.insertaActualizaActividad(session, actividad);
                            } else {
                                mensaje.mensajeConsola("no existe dicha actividad.");
                            }
                        } else {
                            mensaje.mensajeConsola("Socio no existe en la BD");

                        }
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la baja de un socio de una actividad", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }

                    break;
                case 13:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    sc.nextLine();
                    mensaje.mensajeConsola("Introduce la Actividad: ");
                    act = sc.nextLine();
                    try {

                        Actividad actividad = session.get(Actividad.class, act);
                        Set<Socio> socioset = actividad.getSocioSet();
                        if (socioset != null) {
                            vSocios.mostrarNombreyTelefono(socioset);
                        } else {
                            mensaje.mensajeConsola("Dicha Actividad no existe en la BD.");
                        }
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la petición de socios de una actividad", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                    break;
                case 14:
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    sc.nextLine();
                    mensaje.mensajeConsola("Introduce el numero de socio: ");
                    act = sc.nextLine();
                    try {

                        Socio actividad = session.get(Socio.class, act);
                        if (actividad != null) {
                            System.out.println("Nombre del socio: " + actividad.getNombre());
                            Set<Actividad> a = actividad.getActividadSet();
                            System.out.println("Nombre del socio: " + a.toString());

                            vSocios.mostrarNombreActividad(a);
                        } else {
                            mensaje.mensajeConsola("Dicha Socio no existe en la BD.");
                        }
                        transaction.commit();
                    } catch (Exception e) {
                        transaction.rollback();
                        mensaje.mensajeConsola("Error en la petición de Actividades", e.getMessage());
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                    break;

                default:
                    mensaje.mensajeConsola("Opcion no valida.");
            }
        } while (opc != 0);
    }*/
