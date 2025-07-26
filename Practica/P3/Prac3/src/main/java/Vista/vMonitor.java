/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Monitor;
import Modelo.Socio;
import java.util.ArrayList;

/**
 *
 * @author wadad
 */
public class vMonitor {

    public void muestraMonitores(Monitor m) {
        System.out.println("codMonitor          nombre          dni            telefono       correo            fechaEntrada");

            
            System.out.print(m.getCodmonitor());
            System.out.print("\t");
            System.out.print(m.getNombre());
            System.out.print("\t\t");
            System.out.print(m.getDni());
            System.out.print("\t");
            System.out.print(m.getTelefono());
            System.out.print("\t");
            System.out.print(m.getCorreo());
            System.out.print("\t");
            System.out.print(m.getFechaentrada());
            System.out.print("\n");
        
    }

    
}
