/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author wadad
 */
public class Socio {

    private String numSocio=null;
    private String nombre=null;
    private String dni=null;
    private String fechaNacimiento=null;
    private String telefono=null;
    private String correo=null;
    private String fechaEntrada=null;
    private String categoria=null;

    public Socio() {
        super();
        this.numSocio=null;
        this.categoria=null;
        this.correo=null;
        this.dni=null;
        this.fechaEntrada=null;
        this.fechaNacimiento=null;
        this.nombre=null;
        this.telefono=null;
    }

    public Socio(String numSocio, String nombre, String dni, String fechaNacimiento, String telefono, String correo, String fechaEntrada, String categoria) {
       super();
        this.numSocio = numSocio;
        this.nombre = nombre;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaEntrada = fechaEntrada;
        this.categoria = categoria;
    }

    public Socio(String numSocio, String nombre, String dni, String fechaEntrada, String categoria) {
        this.numSocio = numSocio;
        this.nombre = nombre;
        this.dni = dni;
        this.fechaEntrada = fechaEntrada;
        this.categoria = categoria;
    }
   
    public String getCategoria() {
        return categoria;
    }

    public String getDni() {
        return dni;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumSocio() {
        return numSocio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumSocio(String numSocio) {
        this.numSocio = numSocio;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

}
