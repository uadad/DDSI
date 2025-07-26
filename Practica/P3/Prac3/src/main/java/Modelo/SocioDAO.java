/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import jdk.jfr.Name;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author wadad
 */
public class SocioDAO {

    public ArrayList<Socio> listaSociosHQL(Session session) throws Exception {

        Query consulta = session.createQuery("SELECT s FROM Socio s", Socio.class);
        ArrayList<Socio> listaS = (ArrayList<Socio>) consulta.list();

        return listaS;
    }

    public ArrayList<Socio> listaSociosSQLNativo(Session session) throws Exception {
        Query consulta = session.createNativeQuery("SELECT * FROM SOCIO S", Socio.class);
        ArrayList<Socio> listaS = (ArrayList<Socio>) consulta.list();

        return listaS;
    }

    public ArrayList<Socio> listaSociosConsultaNombrada(Session session) throws Exception {
        Query consulta = session.createNamedQuery("Socio.findAll", Socio.class);
        ArrayList<Socio> listaS = (ArrayList<Socio>) consulta.list();

        return listaS;
    }

    public ArrayList<Object[]> ListarNombreyTelefono(Session session) throws Exception {
        Query consulta = session.createNativeQuery("Select s.nombre , s.telefono From SOCIO s");
        ArrayList<Object[]> listaS = (ArrayList<Object[]>) consulta.list();
        return listaS;

    }

    public ArrayList<Object[]> ListarNombreyCategoria(Session session, char cat) throws Exception {
        Query consulta = session.createQuery("Select s.nombre , s.categoria From Socio s Where s.categoria= :cate");
        consulta.setParameter("cate", cat);
        ArrayList<Object[]> listaS = (ArrayList<Object[]>) consulta.list();

        return listaS;

    }

    public void insertaActualizaSocio(Session session, Socio s) throws Exception {
        session.saveOrUpdate(s);
    }

    public void eliminaSocio(Session session,Socio s) throws Exception {
        session.delete(s);
    }
}
