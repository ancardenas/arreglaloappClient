package com.Arreglalo.arreglalo;

import java.io.Serializable;

public class Fixer implements Serializable {
    private int id;
    private String nombre;
    private long telefono;
    private String[] trabajos = new String[5];
    private double calificacion;
    private String correo;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String[] getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(String[] trabajos) {
        this.trabajos = trabajos;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }


    public Fixer(int id, String nombre, long telefono, String[] trabajos, double calificacion, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.trabajos = trabajos;
        this.calificacion = calificacion;
        this.correo = correo;
    }
    public Fixer(){

    }

    public void aceptSer(){

    }
    public void desplazarse(){

    }
    public void finServicio(){

    }
}
