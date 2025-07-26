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
public class MonitorDAO {
    
    
     public ArrayList<Monitor> listaMonitores(Session session) throws Exception { //devolver un array list de todos los monitores de la BD.
        Query consulta = session.createNativeQuery("SELECT * FROM MONITOR M", Monitor.class);
        ArrayList<Monitor> listaS = (ArrayList<Monitor>) consulta.list();
        return listaS;
    }
     
    public ArrayList<Monitor> listaMonitoresPorCodigo(Session session) throws Exception {//devolver los monitores ordenados por codigo
        Query consulta = session.createQuery("Select m From Monitor m  order by m.codmonitor", Monitor.class);
        ArrayList<Monitor> listaM = (ArrayList<Monitor>) consulta.list();

        return listaM;
    }
    public String DevolverUltimoCodigo(Session session){  // devolver el codgio mas grnade de todos los monitores.
        Query consulta=session.createQuery("Select MAX(m.codmonitor) from Monitor m");
        return (String)consulta.getSingleResult();
    }
    public void insertaActualizaMonitor(Session session, Monitor m) { //insertar o actualizar un monitor
        session.saveOrUpdate(m);
    }
    public void eliminaMonitor(Session session,Monitor m) throws Exception { //eliminar un monitor
        session.delete(m);
    }
    
}
