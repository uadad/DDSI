/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Vista.VinfoBD;
import Vista.VistaMensaje;
import java.sql.SQLException;

/**
 *
 * @author wadad
 */
public class ControladorLogin {

    private Conexion conexion = null;
    private VistaMensaje vMensaje = null;
    private VinfoBD vInfo=null;
    private ControladorPrincipal cp=null;
    public Conexion ConectarBD() {
        try {
            String server = "M";
            String ip = "172.18.1.241";
            String bd = "DDSI_012";
            String u = "DDSI_012";
            String p = "klKybB6w";
            conexion = new Conexion(server, ip, bd, u, p);
            vMensaje.mensajeConsola("Conexión Correcta");
        } catch (SQLException sqle) {
            vMensaje.mensajeConsola("Error de conexión ", sqle.getMessage());
        } catch (ClassNotFoundException ex) {
            vMensaje.mensajeConsola("Error indeterminado ", ex.getMessage());
        }
        return conexion;
    }
    

    public ControladorLogin() throws SQLException {
        vMensaje = new VistaMensaje();
        vInfo= new VinfoBD();
        conexion=ConectarBD();
        Datos();
        cp=new ControladorPrincipal(conexion);
        
        desconectarBD();
    }
    public void desconectarBD(){
        try {
            conexion.desconexion();
            vMensaje.mensajeConsola("Desconexion Correcta");
        } catch (SQLException e) {
             vMensaje.mensajeConsola("Error de desconexión ", e.getMessage());
        }
    }
    private void Datos(){
        try {
            vInfo.infoMetadatos(conexion.informacionBD());
        } catch (SQLException e) {
            vMensaje.mensajeConsola("Error de conexión ", e.getMessage());
        }
    }
}
