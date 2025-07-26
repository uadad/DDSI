/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import Vista.vSocios;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wadad
 */
public class utilTablasSocio {

    private DefaultTableModel modeloTablaSocios;

    private vSocios vSocio;

    public void inicializarTablaSocios(vSocios vSocio) {
        this.vSocio = vSocio;

        modeloTablaSocios = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vSocio.modeloTablaSocios.setModel(modeloTablaSocios);

    }

    public void dibujarTablaSocios(vSocios vS) {

        inicializarTablaSocios(vS);

        String[] s = {"Código", "Nombre", "DNI", "Fecha de Nacimiento", "Telefono", "Correo", "Fecha Ide Alta", "Cat."};
        modeloTablaSocios.setColumnIdentifiers(s);

        vSocio.modeloTablaSocios.getTableHeader().setResizingAllowed(false);
        vSocio.modeloTablaSocios.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vSocio.modeloTablaSocios.getColumnModel().getColumn(0).setPreferredWidth(60);
        vSocio.modeloTablaSocios.getColumnModel().getColumn(1).setPreferredWidth(200);
        vSocio.modeloTablaSocios.getColumnModel().getColumn(2).setPreferredWidth(100);
        vSocio.modeloTablaSocios.getColumnModel().getColumn(3).setPreferredWidth(80);
        vSocio.modeloTablaSocios.getColumnModel().getColumn(4).setPreferredWidth(80);
        vSocio.modeloTablaSocios.getColumnModel().getColumn(5).setPreferredWidth(140);
        vSocio.modeloTablaSocios.getColumnModel().getColumn(6).setPreferredWidth(80);
        vSocio.modeloTablaSocios.getColumnModel().getColumn(7).setPreferredWidth(30);

    }

    public void rellenarTablaSocios(ArrayList<Socio> s) {
        Object[] f = new Object[8];
        for (Socio socio : s) {
            f[0] = socio.getNumerosocio();
            f[1] = socio.getNombre();
            f[2] = socio.getDni();
            f[3] = socio.getFechanacimiento();
            f[4] = socio.getTelefono();
            f[5] = socio.getCorreo();
            f[6] = socio.getFechaentrada();
            f[7] = socio.getCategoria();
            modeloTablaSocios.addRow(f);
        }
    }

    public void vaciarTablaSocios() {
        while (modeloTablaSocios.getRowCount() > 0) {
            modeloTablaSocios.removeRow(0);
        }
    }
}
