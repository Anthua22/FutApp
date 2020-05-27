package com.example.futapp.ClasesPojos;

import java.io.Serializable;

public class Jugadores implements Serializable {
    int id;
    String nombre_completo;
    String foto;
    int sancionado;
    String categoria;
    String fecha_nacimiento;
    String dni;
    int equipo;
    boolean titular;
    boolean portero;
    boolean capitan;
    boolean suplente;
    int dorsal;

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public boolean isCapitan() {
        return capitan;
    }

    public void setCapitan(boolean capitan) {
        this.capitan = capitan;
    }

    public boolean isTitular() {
        return titular;
    }

    public void setTitular(boolean titular) {
        this.titular = titular;
    }

    public boolean isPortero() {
        return portero;
    }

    public void setPortero(boolean portero) {
        this.portero = portero;
    }

    public boolean isSuplente() {
        return suplente;
    }

    public void setSuplente(boolean suplente) {
        this.suplente = suplente;
    }


    public Jugadores(){

    }

    public Jugadores(int id, String nombre_completo, String foto, int sancionado, String categoria, String fecha_nacimiento, String dni, int equipo) {
        this.id = id;
        this.nombre_completo = nombre_completo;
        this.foto = foto;
        this.sancionado = sancionado;
        this.categoria = categoria;
        this.fecha_nacimiento = fecha_nacimiento;
        this.dni = dni;
        this.equipo = equipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getSancionado() {
        return sancionado;
    }

    public void setSancionado(int sancionado) {
        this.sancionado = sancionado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }


    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEquipo() {
        return equipo;
    }

    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return "Jugadores{" +
                "id=" + id +
                ", nombre_completo='" + nombre_completo + '\'' +
                ", foto='" + foto + '\'' +
                ", sancionado=" + sancionado +
                ", categoria='" + categoria + '\'' +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", dni='" + dni + '\'' +
                ", equipo=" + equipo +
                '}';
    }
}
