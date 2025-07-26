/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author wadad
 */
public class SocioDAO {

    private Conexion conn = null;
    private PreparedStatement ps = null;

    public SocioDAO(Conexion conn) {
        this.conn = conn;
    }

    public ArrayList<Socio> ListarSocios() throws SQLException {
        ArrayList<Socio> ListaSocios = new ArrayList<>();

        String consulta = "Select * from SOCIO";
        ps = conn.getConexion().prepareStatement(consulta);
        ResultSet res = ps.executeQuery();
        while (res.next()) {
            Socio s = new Socio(res.getString(1), res.getString(2),
                    res.getString(3), res.getString(4),
                    res.getString(5), res.getString(6),
                    res.getString(7), res.getString(8));
            ListaSocios.add(s);
        }
        return ListaSocios;
    }

    public boolean ConsultarSocio(String numS) throws SQLException {

        String consulta = "Select * from SOCIO where numeroSocio=?";

        ps = conn.getConexion().prepareStatement(consulta);
        ps.setString(1, numS);
        ResultSet res = ps.executeQuery();
        return res.next();

    }

    public void AltaSocio(Socio s) throws SQLException {
        ps = conn.getConexion().prepareStatement("INSERT INTO SOCIO VALUES(?,?,?,?,?,?,?,?)");
        ps.setString(1, s.getNumSocio());
        ps.setString(2, s.getNombre());
        ps.setString(3, s.getDni());    
        ps.setString(4, s.getFechaNacimiento());
        ps.setString(5, s.getTelefono());
        ps.setString(6, s.getCorreo());
        ps.setString(7, s.getFechaEntrada());
        ps.setString(8, s.getCategoria());
        
        ps.executeUpdate();
        
       

    }

    public void BajaSocio(String nSocio) throws SQLException {
          
        String consulta="DELETE FROM SOCIO WHERE numeroSocio= ?";
        ps=conn.getConexion().prepareStatement(consulta);
        ps.setString(1,nSocio);
        ps.executeUpdate();
        
    }

    public void ActualizarSocio(String nSocio,String campo,String valor) throws SQLException {
       
        String consulta="UPDATE SOCIO"
                + " SET "+campo+ "=? "
                + "WHERE numeroSocio=?";
        System.out.println(campo);
        ps=conn.getConexion().prepareStatement(consulta);
        ps.setString(1, valor);
        ps.setString(2, nSocio);
       ps.executeUpdate();
    }
}
