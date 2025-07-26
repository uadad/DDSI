/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author wadad
 */
public class Conexion {
    private Connection conn=null;
    
    public Conexion (String sgbd, String ip, String servicio_bd, String usuario, String password) throws ClassNotFoundException,SQLException{
        if(sgbd.equals("M")){
           conn= DriverManager.getConnection("jdbc:mariadb://"+ip+":3306/"+servicio_bd,usuario,password); 
        } else {
           conn= DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":1521:"+servicio_bd,usuario,password); 
        }
    }
    
    public Connection getConexion(){
       return conn; 
    }
    public void desconexion() throws SQLException{
        if(conn!=null)
             conn.close();
    }
    public DatabaseMetaData informacionBD() throws SQLException{
        return conn.getMetaData();
    }
}
