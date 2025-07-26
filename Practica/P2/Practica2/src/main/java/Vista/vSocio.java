/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Socio;
import java.util.ArrayList;

/**
 *
 * @author wadad
 */
public class vSocio {
    public void muestraSocios(ArrayList<Socio> s){
        System.out.println("numero          nombre          dni            telefono       correo          categoria        fechaNAcimiento             fechaEntrada");
        
        for (Socio i: s) {
            System.out.print(i.getNumSocio()); 
            System.out.print("\t");
            System.out.print(i.getNombre());
            System.out.print("\t\t");
            System.out.print(i.getDni());
            System.out.print("\t");
            System.out.print(i.getTelefono());
            System.out.print("\t");
            System.out.print(i.getCorreo());
            System.out.print("\t");
            System.out.print(i.getCategoria());
            System.out.print("\t");
            System.out.print(i.getFechaNacimiento());
            System.out.print("\t");
            System.out.print(i.getFechaEntrada());
            System.out.print("\n");
        }
    }
}
