/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Aplicacion;

import Controlador.ControladorLogin;
import Controlador.ControladorPrincipal;
import java.sql.SQLException;

/**
 *
 * @author wadad
 */
public class Practica2 {

    public static void main(String[] args) throws SQLException {
        ControladorLogin ConL = new ControladorLogin();
        ConL.desconectarBD();
        
         
    }
}
