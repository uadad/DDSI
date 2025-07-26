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
    
    
    public Monitor ListarResponsables(Session session,String id){ //listar todos los campos del monitor que se pasa su id 
        Query consulta=session.createNativeQuery("SELECT m.* FROM ACTIVIDAD a INNER JOIN MONITOR m ON a.monitorresponsable=m.codmonitor Where a.idactividad= :ida ",Monitor.class); 
        consulta.setParameter("ida", id); //meter el id en la variable para comprobarla con el campo
        
        Monitor monitor= (Monitor) consulta.getSingleResult(); //recoge el resultado y castigarlo aq sea monitor
        return monitor;
    }
    public ArrayList<Socio> ListarSocios(Session session,String id){//el mismo que el anterior pero con soico
        Query consulta=session.createNativeQuery("SELECT s.* FROM (ACTIVIDAD a INNER JOIN REALIZA r ON a.idactividad=r.idActividad)INNER JOIN SOCIO s ON r.numerosocio=s.numeroSocio Where a.idactividad= :ida ",Socio.class);
        consulta.setParameter("ida", id);
        ArrayList<Socio> lista= (ArrayList<Socio>) consulta.list();
        return lista;
    }
     public void insertaActualizaActividad(Session session, Actividad a) throws Exception {//insertar o actualizar una actividad
        session.saveOrUpdate(a);
    }
     public ArrayList<Actividad> ListarActividades(Session session){ //listar todas las actividades y devolverlas
         Query consulta=session.createQuery("Select a from Actividad a",Actividad.class);
         ArrayList<Actividad> actividades=(ArrayList<Actividad>) consulta.list();
         return actividades;
     }
     public String ListarActividad(Session session,String nom){ //consultar el id de la actividad pasad por parametro
         Query consulta=session.createQuery("Select a.idactividad FROM Actividad a WHERE a.nombre = :nombre");
         consulta.setParameter("nombre", nom);
        
        return (String) consulta.getSingleResult();
     }
     
}
