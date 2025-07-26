/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import Modelo.Monitor;
import Modelo.Socio;
import Modelo.SocioDAO;
import Vista.vMensaje;
import Vista.vMenu;
import Vista.vMonitor;
import Vista.vSocio;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author wadad
 */
public class ControladorPrincipal {

    private Session session;
    private Transaction transaction;

    public ControladorPrincipal(SessionFactory sessionFactory) {
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
    }

}
