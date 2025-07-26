/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author wadad
 */
public class VistaMensaje {

    public void mensajeConsola(String texto) {
        System.out.println("***************************************");
        System.out.println(texto);
        System.out.println("***************************************");
    }

   public void mensajeConsola(String texto,String error){
         System.out.println("***************************************");
        System.out.println(texto);
        System.out.println("***************************************");
        System.out.println(error);
        System.out.println("***************************************");   
   }
}
