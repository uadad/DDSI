/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author wadad
 */
public class Excepciones {

    public Excepciones() {
    }
    
    public void comprobar_Fecha(Date date) throws Exception {
        Date da = new Date();
        if (da.before(date)) {
            throw new Exception("Error,la fecha de netrada debe ser la correcta.");
        }
    }

    public void comprobar_Telefono(String dato) throws Exception {
        Pattern patron = Pattern.compile("[0-9]{9}");
        Matcher mat = patron.matcher(dato);
        if (!mat.matches()) {
            throw new Exception("El campo telefono debe de tener 9 digitos.");
        }
    }

    public void comprobar_DNI(String dato) throws Exception {
        Pattern patron = Pattern.compile("[0-9]{8}[A-Z]");
        Matcher mat = patron.matcher(dato);
        if (!mat.matches()) {
            throw new Exception("El campo dni debe de tener 8 digitos y una letra mayuscula.");
        }
    }

    public void comprobar_Correo(String dato) throws Exception {
        Pattern patron2 = Pattern.compile("[a-zA-Z0-9_.]{3,20}@[a-zA-Z0-9.]{3,20}");
        Matcher mat2 = patron2.matcher(dato);
        if (!mat2.matches()) {
            throw new Exception("El campo correo debe ser valido.");
        }
    }

    public void comprobar_Mayor18(String dato) throws Exception {
        String[] mayor = dato.split("/");
        int año_nac = Integer.parseInt(mayor[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int año_actual = calendar.get(Calendar.YEAR);
        if (año_actual - año_nac < 18) {
            throw new Exception("EL socio debe tener al menos 18 años.");
        }
    }
    public String Establecerfecha(String s) {
        String[] aux = s.split(" ");

        String e = aux[2] + "/"; //Dia
        switch (aux[1]) {
            case "Jan":
                e += "01";
                break;
            case "Feb":
                e += "02";
                break;
            case "Mar":
                e += "03";
                break;
            case "Apr":
                e += "04";
                break;
            case "May":
                e += "05";
                break;
            case "Jun":
                e += "06";
                break;
            case "Jul":
                e += "07";
                break;
            case "Aug":
                e += "08";
                break;
            case "Sep":
                e += "09";
                break;
            case "Oct":
                e += "10";
                break;
            case "Nov":
                e += "11";
                break;
            case "Dec":
                e += "12";
                break;

        }
        e += "/" + aux[5];
        return e;
    }
}
