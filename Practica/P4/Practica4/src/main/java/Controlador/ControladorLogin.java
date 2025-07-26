/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Config.*;
import Vista.VistaLogin;
import Vista.vMensaje;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;

/**
 *
 * @author wadad
 */
public class ControladorLogin implements ActionListener {

    private SessionFactory sessionFactory;
    private vMensaje vmensaje;
    private VistaLogin vlogin;
    private ControladorPrincipal controladorP;

    private void conectarBD() {
        try {
            String s = (String) vlogin.Servidor.getSelectedItem();
            if (s.equals("Oracle")) {
                sessionFactory = HibernateUtilOracle.getSessionFactory();
            } else if (s.equals("MariaDB")) {
                sessionFactory = HibernateUtilMariaDB.getSessionFactory();
            }
            vmensaje.mensajeConsola("Conexión Correcta con Hibernate");
        } catch (ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            vmensaje.mensajeConsola("Error en la conexión. Revise el fichero .cfg.xml: " + cause.getMessage());
        }
    }

    public ControladorLogin() {
        vmensaje = new vMensaje();
        vlogin = new VistaLogin();

        addListeners();
        vlogin.setLocationRelativeTo(null);
        vlogin.setVisible(true);

        vlogin.Servidor.setSelectedIndex(0);

    }

    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Conectar":
                conectarBD();
                vlogin.dispose();
                 {
                    try {
                        controladorP = new ControladorPrincipal(sessionFactory);
                    } catch (Exception ex) {
                        Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            case "Salir":
                vlogin.dispose();
                System.exit(0);
                break;
        }
    }

    private void addListeners() {
        vlogin.jButtonConectar.addActionListener(this);
        vlogin.jButtonSalir.addActionListener(this);
    }

}
