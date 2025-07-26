/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Monitor;
import Vista.vMonitores;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wadad
 */
public class utilTablasMonitores {

    private DefaultTableModel modeloTablaMonitores;

    private vMonitores vMonitor;

    public void inicializarTablaMonitores(vMonitores vMonitor) {
        this.vMonitor = vMonitor;

        modeloTablaMonitores = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vMonitor.jTableMonitores.setModel(modeloTablaMonitores);

    }

    public void dibujarTablaMonitores(vMonitores vM) {

        inicializarTablaMonitores(vM);

        String[] s = {"Código", "Nombre", "DNI", "Telefono", "Correo", "Fecha Incroporación", "Nick"};

        modeloTablaMonitores.setColumnIdentifiers(s);

        vMonitor.jTableMonitores.getTableHeader().setResizingAllowed(false);
        vMonitor.jTableMonitores.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vMonitor.jTableMonitores.getColumnModel().getColumn(0).setPreferredWidth(40);
        vMonitor.jTableMonitores.getColumnModel().getColumn(1).setPreferredWidth(240);
        vMonitor.jTableMonitores.getColumnModel().getColumn(2).setPreferredWidth(70);
        vMonitor.jTableMonitores.getColumnModel().getColumn(3).setPreferredWidth(70);
        vMonitor.jTableMonitores.getColumnModel().getColumn(4).setPreferredWidth(200);
        vMonitor.jTableMonitores.getColumnModel().getColumn(5).setPreferredWidth(150);
        vMonitor.jTableMonitores.getColumnModel().getColumn(6).setPreferredWidth(60);

    }

    public void rellenarTablaMonitores(ArrayList<Monitor> m) {
        Object[] f = new Object[7];
        for (Monitor monitor : m) {
            f[0] = monitor.getCodmonitor();
            f[1] = monitor.getNombre();
            f[2] = monitor.getDni();
            f[3] = monitor.getTelefono();
            f[4] = monitor.getCorreo();
            f[5] = monitor.getFechaentrada();
            f[6] = monitor.getNick();
            modeloTablaMonitores.addRow(f);
        }
    }

    public void vaciarTablaMonitores() {
        while (modeloTablaMonitores.getRowCount() > 0) {
            modeloTablaMonitores.removeRow(0);
        }
    }
}
