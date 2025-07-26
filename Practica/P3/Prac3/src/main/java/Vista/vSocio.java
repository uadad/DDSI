/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Actividad;
import Modelo.Socio;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author wadad
 */
public class vSocio {

    public void muestraSocios(ArrayList<Socio> s) {
        System.out.println("numero          nombre          dni            telefono       correo          categoria        fechaNacimiento             fechaEntrada");

        for (Socio i : s) {
            System.out.print(i.getNumerosocio());
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
            System.out.print(i.getFechanacimiento());
            System.out.print("\t");
            System.out.print(i.getFechaentrada());
            System.out.print("\n");
        }
    }

    public void mostrarNombreyTelefono(ArrayList<Object[]> s) {
        System.out.println("Nombre\t\t\tTelefono");
        for (Object[] socio: s) {
            System.out.println(socio[0] +"\t"+socio[1]);
        }
    }
    public void mostrarNombreyTelefono(Set<Socio> s) {
        System.out.println("Nombre\t\t\tTelefono");
        for (Socio socio: s) {
            System.out.println(socio.getNombre() +"\t"+socio.getTelefono());
        }
    }
    public void mostrarNombreActividad(Set<Actividad> s) {
        System.out.println("Nombre\tPrecio");
        for (Actividad a: s) {
            System.out.println(a.getNombre() +"\t"+a.getPreciobasemes());
        }
    }

    public void mostrarNombreyCategoria(ArrayList<Object[]> listaSocios) {
        System.out.println("Nombre\t\t\tTelefono");
       for (Object[] socio: listaSocios) {
            System.out.println(socio[0] +"\t"+socio[1]);
        }
    }
}
