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
public class ActividadDAO {
    
    
    public Monitor ListarResponsables(Session session,String id){
        Query consulta=session.createNativeQuery("SELECT m.* FROM ACTIVIDAD a INNER JOIN MONITOR m ON a.monitorresponsable=m.codmonitor Where a.idactividad= :ida ",Monitor.class);
        consulta.setParameter("ida", id);
        
        Monitor monitor= (Monitor) consulta.getSingleResult();
        return monitor;
    }
    public ArrayList<Socio> ListarSocios(Session session,String id){
        Query consulta=session.createNativeQuery("SELECT s.* FROM (ACTIVIDAD a INNER JOIN REALIZA r ON a.idactividad=r.idActividad)INNER JOIN SOCIO s ON r.numerosocio=s.numeroSocio Where a.idactividad= :ida ",Socio.class);
        consulta.setParameter("ida", id);
        ArrayList<Socio> lista= (ArrayList<Socio>) consulta.list();
        return lista;
    }
     public void insertaActualizaActividad(Session session, Actividad a) throws Exception {
        session.saveOrUpdate(a);
    }
}
