/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import Modelo.Socio;
import Vista.vActividades;
import Vista.vCuotaSocio;
import Vista.vMensaje;
import Vista.vPrecioActividad;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author wadad
 */
public class ControladorActividades implements ActionListener {

    private Session session;
    private Transaction transaction;
    private vMensaje mensaje;
    private SessionFactory sessionFactory;
    private vPrecioActividad vActPrecio;
    private DefaultTableModel tablaAct = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private ActividadDAO actividaddao;
    private final vCuotaSocio vCuota;

    public ControladorActividades(SessionFactory sessionFactory, vPrecioActividad vActPrecio, vCuotaSocio vCuota) {
        this.sessionFactory = sessionFactory;
        this.vActPrecio = vActPrecio;
        this.vCuota = vCuota;

        actividaddao = new ActividadDAO();
        mensaje = new vMensaje();

        tablaAct.setColumnIdentifiers(new String[]{"ID", "Nombre", "Precio"});
        vActPrecio.jTablePrecios.setModel(tablaAct);

        rellenarTabla();
        addListeners();

    }

    private void addListeners() {
        vActPrecio.ActualizarPrecio.addActionListener(this);
        vActPrecio.VolverAtras.addActionListener(this);
        vCuota.VerCuota.addActionListener(this);
        vCuota.VolverAtras.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Actualizar Precio":
                if (!vActPrecio.jPrecioText.getText().equals("")) {

                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    try {
                        ArrayList<Actividad> lista = actividaddao.ListarActividades(session);

                        for (Actividad act : lista) {
                            int precio = act.getPreciobasemes().intValue();
                            precio += Integer.parseInt(vActPrecio.jPrecioText.getText());
                            act.setPreciobasemes(precio);
                            actividaddao.insertaActualizaActividad(session, act);
                        }

                        transaction.commit();
                    } catch (Exception ex) {
                        transaction.rollback();
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                    rellenarTabla();

                } else {
                    mensaje.mensajeConsola("debe introducir un precio primero.");
                }
                break;

            case "Volver Atrás":
                vActPrecio.dispose();
                vActPrecio.jPrecioText.setText("");
                break;

            case "Ver Cuota":
                vCuota.jTextFieldActividades.setText("");
                vCuota.jTextFieldActividades.setEditable(false);
                vCuota.jTextFieldTotal.setText("");
                vCuota.jTextFieldTotal.setEditable(false);
                
                if (!vCuota.jTextSocioCuota.getText().equals("")) {
                    vCuota.dispose();
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    try {
                        
                        Socio s = session.get(Socio.class, vCuota.jTextSocioCuota.getText());
                        Set<Actividad> actividades = s.getActividadSet();
                        vCuota.jTextFieldActividades.setText(String.valueOf(actividades.size()));
                        vCuota.jTextFieldActividades.setEditable(false);
                        int total = 0;
                        for (Actividad act : actividades) {
                            total += act.getPreciobasemes().intValue();
                        }
                        vCuota.jTextFieldTotal.setText(String.valueOf(total));
                        vCuota.jTextFieldTotal.setEditable(false);
                        transaction.commit();
                    } catch (Exception ex) {
                        transaction.rollback();
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                } else {
                    mensaje.mensajeConsola("debe introducir un codigo de socio primero.");
                }
                vCuota.setLocationRelativeTo(null);
                vCuota.setVisible(true);
                break;

            case "Volver Atras":
                vCuota.dispose();
                vaciarCuota();
                break;
        }
    }

    private void rellenarTabla() {
        while (tablaAct.getRowCount() > 0) {
            tablaAct.removeRow(0);
        }
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            ArrayList<Actividad> lista = actividaddao.ListarActividades(session);

            for (Actividad act : lista) {
                tablaAct.addRow(new Object[]{act.getIdactividad(), act.getNombre(), act.getPreciobasemes()});
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

    private void vaciarCuota() {
        vCuota.jTextFieldActividades.setText("");
        vCuota.jTextFieldActividades.setEditable(false);
        vCuota.jTextFieldTotal.setText("");
        vCuota.jTextFieldTotal.setEditable(false);
        vCuota.jTextSocioCuota.setText("");

    }
}
