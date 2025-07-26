/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.Socio;
import Modelo.SocioDAO;
import Vista.vCategorias;
import Vista.vCuotaActividad;
import Vista.vMensaje;
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
public class ControladorExamen implements ActionListener {

    private Session session;
    private Transaction transaction;
    private vMensaje mensaje;
    private SessionFactory sessionFactory;
    private vCategorias vCategoria;
    private vCuotaActividad vCuota;
    private DefaultTableModel tabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private SocioDAO sociodao;

    public ControladorExamen(SessionFactory sessionFactory, vCategorias vCategoria, vCuotaActividad vCuota) {
        this.sessionFactory = sessionFactory;
        this.vCategoria = vCategoria;
        this.vCuota = vCuota;
        sociodao = new SocioDAO();
        mensaje = new vMensaje();

        tabla.setColumnIdentifiers(new String[]{"Nombre", "DNI", "Categoria"});
        this.vCategoria.jTableCategorias.setModel(tabla);

        rellenarTabla();

        addListeners();
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Subir":
                rellenarTabla();
                int opc = mensaje.BajaDialog(vCategoria, "¿Seguro que desea incrementar la categoria a todos los socios?");
                if (opc == JOptionPane.YES_OPTION) {
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    try {
                        char c = 'B';
                        ArrayList<Socio> listaSocios = new ArrayList<>();

                        for (int i = 0; i < 4; i++) {
                            listaSocios = sociodao.devolverSociosCat(session, c);
                            for (Socio s : listaSocios) {
                                char cat = (char) (c - 1);
                                s.setCategoria(cat);
                                sociodao.insertaActualizaSocio(session, s);
                            }
                            c++;
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
                }
                break;

            case "Bajar":
                rellenarTabla();
                opc = mensaje.BajaDialog(vCategoria, "¿Seguro que desea decrementar la categoria a todos los socios?");
                if (opc == JOptionPane.YES_OPTION) {
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    try {
                        char c = 'D';
                        ArrayList<Socio> listaSocios = new ArrayList<>();

                        for (int i = 0; i < 4; i++) {
                            listaSocios = sociodao.devolverSociosCat(session, c);
                            for (Socio s : listaSocios) {
                                char cat = (char) (c + 1);
                                s.setCategoria(cat);
                                sociodao.insertaActualizaSocio(session, s);
                            }
                            c--;
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
                }
                break;

            case "Ver Cuota":
                vaciarCuota();
                if (!vCuota.jTextField1.getText().equals("")) {
                    session = sessionFactory.openSession();
                    transaction = session.beginTransaction();
                    try {
                        Actividad a = session.get(Actividad.class, vCuota.jTextField1.getText());

                        vCuota.jTextField2.setText(a.getMonitorresponsable().getNombre());
                        vCuota.jTextField2.setEditable(false);
                        int numSocio = a.getSocioSet().size();
                        vCuota.jTextField3.setText("" + numSocio);
                        vCuota.jTextField3.setEditable(false);
                        int Precio = a.getPreciobasemes();
                        vCuota.jTextField4.setText("" + Precio);
                        vCuota.jTextField4.setEditable(false);

                        vCuota.jTextField5.setText("" + (Precio * numSocio));
                        vCuota.jTextField5.setEditable(false);
                        Set<Socio> listado = a.getSocioSet();
                        int total = 0;
                        for (Socio s : listado) {
                            char cat = s.getCategoria();
                            switch (cat) {
                                case 'B':
                                    total += ((Precio * 90) / 100);
                                    break;
                                case 'C':
                                    total += ((Precio * 80) / 100);
                                    break;
                                case 'D':
                                    total += ((Precio * 70) / 100);
                                    break;
                                case 'E':
                                    total += ((Precio * 60) / 100);
                                    break;
                            }
                        }
                        vCuota.jTextField6.setText("" + total);
                        vCuota.jTextField6.setEditable(false);
                        transaction.commit();
                    } catch (Exception ex) {
                        transaction.rollback();
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }
                } else {
                    mensaje.mensajeConsola("Debes introudcir un codigo de activiadad.");
                }
                break;
        }
    }

    private void addListeners() {
        vCategoria.jButtonBajar.addActionListener(this);
        vCategoria.jButtonSubir.addActionListener(this);
        vCuota.jButtonCuota.addActionListener(this);
    }

    private void rellenarTabla() {
        while (tabla.getRowCount() > 0) {
            tabla.removeRow(0);
        }
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            char c = 'A';
            ArrayList<Socio> listaSocios = new ArrayList<>();
            for (int i = 0; i < 5; i++) {

                listaSocios = sociodao.devolverSociosCat(session, c);
                for (Socio s : listaSocios) {
                    tabla.addRow(new Object[]{s.getNombre(), s.getDni(), s.getCategoria()});
                }

                c++;
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
        vCuota.jTextField2.setText("");
        vCuota.jTextField2.setEditable(false);
        vCuota.jTextField3.setText("");
        vCuota.jTextField3.setEditable(false);
        vCuota.jTextField4.setText("");
        vCuota.jTextField4.setEditable(false);
        vCuota.jTextField5.setText("");
        vCuota.jTextField5.setEditable(false);
        vCuota.jTextField6.setText("");
        vCuota.jTextField6.setEditable(false);
    }
}
