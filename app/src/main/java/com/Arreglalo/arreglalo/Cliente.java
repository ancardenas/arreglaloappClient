package com.Arreglalo.arreglalo;

import android.content.Intent;

import java.io.Serializable;

public class Cliente implements Serializable {
    private  String nombre;
    private  String numero;
    private  double calificacion;
    private  String direccion;
    private  String correo;
    private  String ciudad;
    private  String detalles;
    private  String contrasena;
    private  int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente(String nombre, String numero, String direccion, String correo, String ciudad, String detalles) {
        this.nombre = nombre;
        this.numero = numero;
        this.direccion = direccion;
        this.correo = correo;
        this.ciudad = ciudad;
        this.detalles = detalles;


    }
    public Cliente(){

    }


    public boolean initSesion(String correo, String contrasena){
        if(this.correo.equals(correo)   && this.contrasena.equals(contrasena)   ){
            return true;
        }else{
            return false;
        }


    }
    public void closeSesion(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
