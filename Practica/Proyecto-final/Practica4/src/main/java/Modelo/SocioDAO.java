/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author wadad
 */
public class SocioDAO {

    public ArrayList<Socio> listaSociosHQL(Session session) throws Exception { //listar todos los socios mediante consulta HQL

        Query consulta = session.createQuery("SELECT s FROM Socio s", Socio.class);
        ArrayList<Socio> listaS = (ArrayList<Socio>) consulta.list();

        return listaS;
    }

    public ArrayList<Socio> listaSociosSQLNativo(Session session) throws Exception { //listar todos los socios mediante consulta SQLNativo
        Query consulta = session.createNativeQuery("SELECT * FROM SOCIO S", Socio.class);
        ArrayList<Socio> listaS = (ArrayList<Socio>) consulta.list();

        return listaS;
    }

    public ArrayList<Socio> listaSociosConsultaNombrada(Session session) throws Exception { //listar todos los socios mediante consulta Nombrada
        Query consulta = session.createNamedQuery("Socio.findAll", Socio.class);
        ArrayList<Socio> listaS = (ArrayList<Socio>) consulta.list();

        return listaS;
    }

    public ArrayList<Object[]> ListarNombreyTelefono(Session session) throws Exception { //listar los nombres y los telefonos de todos los socios
        Query consulta = session.createNativeQuery("Select s.nombre , s.telefono From SOCIO s");
        ArrayList<Object[]> listaS = (ArrayList<Object[]>) consulta.list();
        return listaS;

    }

    public ArrayList<Object[]> ListarNombreyCategoria(Session session, char cat) throws Exception { //listar los nombres y las categorias de todos los socios de la categroia pasada por parametro
        Query consulta = session.createQuery("Select s.nombre , s.categoria From Socio s Where s.categoria= :cate");
        consulta.setParameter("cate", cat);
        ArrayList<Object[]> listaS = (ArrayList<Object[]>) consulta.list();

        return listaS;

    }

    public String DevolverUltimoSocio(Session session) {  //devolver el codigo del socio mas grande de la BD.
        Query consulta = session.createQuery("Select MAX(s.numerosocio) from Socio s");
        return (String) consulta.getSingleResult();
    }

    public ArrayList<Socio> listaSociosPorNumero(Session session) throws Exception { //listar todos los socios ordenados por su codigo
        Query consulta = session.createQuery("Select s From Socio s order by s.numerosocio", Socio.class);
        ArrayList<Socio> listaS = (ArrayList<Socio>) consulta.list();

        return listaS;
    }
    
    public ArrayList<Socio> listaSociosPorCampo(Session session,String campo) throws Exception { //listar todos los socios ordenados por un campo pasado por parametro
        Query consulta = session.createNativeQuery("SELECT S.* FROM SOCIO S ORDER BY :campo", Socio.class);
        consulta.setParameter("campo", campo);
        ArrayList<Socio> listaS = (ArrayList<Socio>) consulta.list();
        return listaS;
    }
    
    public ArrayList<String> listaSociosPorNumeroDevulve(Session session) throws Exception { //devolver todos los codigos de todos los socios de la BD.
        Query consulta = session.createQuery("Select s.numerosocio from Socio s");
        ArrayList<String> listaS = (ArrayList<String>) consulta.list();

        return listaS;
    }

    public void insertaActualizaSocio(Session session, Socio s) throws Exception { //insertar o actualizar un socio en la BD.
        session.saveOrUpdate(s);
    }

    public void eliminaSocio(Session session, Socio s) throws Exception { //eliminar un socio
        session.delete(s);
    }

    public void eliminaSocio(Session session, String s) throws Exception { //eliminar un socio solo con su codigo lo buscas y despúes lo eliminas
        Socio socio = session.get(Socio.class, s);
        session.delete(socio);
    }

    public ArrayList<Socio> devolverSociosCat(Session session, Character c) { //consultar todos los socios de una categoria pasada por parametro
        Query consulta = session.createNativeQuery("SELECT S.* FROM SOCIO S WHERE S.categoria=:cat", Socio.class);
        consulta.setParameter("cat", c);
        ArrayList<Socio> listaS = (ArrayList<Socio>) consulta.list();
        return listaS;
    }
}
