package com.example.futapp.ClasesPojos;

import java.io.Serializable;

public class Equipo implements Serializable {

    int idEquipo;
    String nombre;
    String foto;
    String categoria;
    String provincia;
    String correo;
    String localidad;
    String direccion_campo;

    public Equipo(int idEquipo, String nombre, String foto, String categoria, String provincia, String correo, String localidad, String direccion_campo) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.foto = foto;
        this.categoria = categoria;
        this.provincia = provincia;
        this.correo = correo;
        this.localidad = localidad;
        this.direccion_campo = direccion_campo;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion_campo() {
        return direccion_campo;
    }

    public void setDireccion_campo(String direccion_campo) {
        this.direccion_campo = direccion_campo;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "idEquipo=" + idEquipo +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                ", categoria='" + categoria + '\'' +
                ", provincia='" + provincia + '\'' +
                ", correo='" + correo + '\'' +
                ", localidad='" + localidad + '\'' +
                ", direccion_campo='" + direccion_campo + '\'' +
                '}';
    }
}
