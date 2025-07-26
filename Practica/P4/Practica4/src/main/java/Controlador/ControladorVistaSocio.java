/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import Modelo.Socio;
import Vista.vActividades;
import Vista.vMensaje;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author wadad
 */
public class ControladorVistaSocio implements ActionListener {

    private Session session;
    private Transaction transaction;
    private vMensaje mensaje;
    private SessionFactory sessionFactory;
    private vActividades vActividad;
    private ActividadDAO actividaddao;
    private Socio socio;
    private DefaultTableModel tablaBaja = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel tablaAlta = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ControladorVistaSocio(SessionFactory sessionFactory, Socio socio) {
        this.sessionFactory = sessionFactory;
        this.socio = socio;
        vActividad = new vActividades();
        actividaddao = new ActividadDAO();

        mensaje = new vMensaje();

        tablaBaja.setColumnIdentifiers(new String[]{"ID", "Nombre"});
        tablaAlta.setColumnIdentifiers(new String[]{"ID", "Nombre"});

        vActividad.jTableActividadesAlta1.setModel(tablaAlta);
        vActividad.jTableActividadesBaja.setModel(tablaBaja);
        addListeners();

        rellenarDatos();

        vActividad.setLocationRelativeTo(null);
        vActividad.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "dar de Alta":
                int actualiza = vActividad.jTableActividadesAlta1.getSelectedRow();
                if (actualiza != -1) {
                    int opcion = mensaje.BajaDialog(vActividad, "Desea dar de Alta a ese Socio");
                    if (opcion == JOptionPane.YES_OPTION) {
                        session = sessionFactory.openSession();
                        transaction = session.beginTransaction();

                        try {
                            String actividad = (String) vActividad.jTableActividadesAlta1.getValueAt(actualiza, 1);
                            String id = actividaddao.ListarActividad(session, actividad);
                            Actividad acti = session.get(Actividad.class, id);
                            acti.getSocioSet().add(socio);
                            actividaddao.insertaActualizaActividad(session, acti);
                            transaction.commit();
                        } catch (Exception ex) {
                            transaction.rollback();
                        } finally {
                            if (session != null && session.isOpen()) {
                                session.close();
                            }
                        }

                        mensaje.mensajeConsola("Ese socio ha sido dado de Alta en esa Actividad.");
                        rellenarDatos();

                    } else {
                        mensaje.mensajeConsola("Ese socio no ha sido dado de Alta.");
                    }
                } else {
                    mensaje.mensajeConsola("Error tienes Seleccionar una Actividad.");
                }

                break;

            case "dar de baja":
                actualiza = vActividad.jTableActividadesBaja.getSelectedRow();
                if (actualiza != -1) {
                    int opcion = mensaje.BajaDialog(vActividad, "Desea dar de Baja a ese Socio");
                    if (opcion == 0) {
                        session = sessionFactory.openSession();
                        transaction = session.beginTransaction();

                        try {

                            String actividad = (String) vActividad.jTableActividadesBaja.getValueAt(actualiza, 1);
                            String id = actividaddao.ListarActividad(session, actividad);
                            Actividad acti = session.get(Actividad.class, id);

                            acti.getSocioSet().remove(socio);
                            actividaddao.insertaActualizaActividad(session, acti);

                            transaction.commit();
                        } catch (Exception ex) {
                            transaction.rollback();
                        } finally {
                            if (session != null && session.isOpen()) {
                                session.close();
                            }
                        }

                        mensaje.mensajeConsola("Ese socio ha sido dado de Baja.");
                        rellenarDatos();
                    } else {
                        mensaje.mensajeConsola("Ese socio no ha sido dado de Baja.");
                    }
                } else {
                    mensaje.mensajeConsola("Error tienes Seleccionar una Actividad.");
                }

                break;

        }
    }

    private void addListeners() {
        vActividad.jButtonAltaActividad.addActionListener(this);
        vActividad.jButtonBajaActividad.addActionListener(this);
    }

    private void rellenarDatos() {
        
     while(tablaBaja.getRowCount()>0){
         tablaBaja.removeRow(0);
     }
     while(tablaAlta.getRowCount() >0){
         tablaAlta.removeRow(0);
     }
        
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            Socio s=session.get(Socio.class,socio.getNumerosocio());
         Set<Actividad> lista = s.getActividadSet();
        for (Actividad act : lista) {
            tablaBaja.addRow(new Object[]{act.getIdactividad(), act.getNombre()});
        }
        ArrayList<Actividad> aux=null;
             aux = actividaddao.ListarActividades(session);
             for (Actividad acti : lista) {
            for (int i = 0; i < aux.size(); i++) {
                if (aux.get(i).getIdactividad().equals(acti.getIdactividad())) {
                    aux.remove(acti);
                }
            }
        }
        for (int i = 0; i < aux.size(); i++) {
            tablaAlta.addRow(new Object[]{aux.get(i).getIdactividad(), aux.get(i).getNombre()});
        }
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        

    }
}
