/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Config.*;
import Vista.vMensaje;
import org.hibernate.SessionFactory;

/**
 *
 * @author wadad
 */
public class ControladorLogin {

    private SessionFactory sessionFactory;
    private vMensaje vmensaje=new vMensaje();

    private void conectarBD(String s) {
        try {
            if (s.equals("Oracle")) {
                sessionFactory = HibernateUtilOracle.getSessionFactory();
            } else if (s.equals("MariaDB")) {
                sessionFactory = HibernateUtilMariaDB.getSessionFactory();
            }
            vmensaje.mensajeConsola("Conexión Correcta con Hibernate");
        } catch (ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            System.out.println("Error en la conexión. Revise el fichero .cfg.xml: " + cause.getMessage());
        }
    }
    
    public ControladorLogin(String s) {
        conectarBD(s);
        ControladorPrincipal cp=new ControladorPrincipal(sessionFactory);
        
    }
    
}
