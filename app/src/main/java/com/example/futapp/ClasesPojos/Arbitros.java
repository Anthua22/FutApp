package com.example.futapp.ClasesPojos;

import java.io.Serializable;
import java.util.Objects;

public class Arbitros implements Serializable {
    int id;
    String dni;
    String pass;
    String nombre_completo;
    String foto;
    String email;
    String fecha_nacimiento;
    String provincia;
    String localidad;
    String cp;
    String categoria;
    String telefono;

    public Arbitros() {
    }

    public Arbitros(String dni, String pass) {
        this.dni = dni;
        this.pass = pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arbitros arbitros = (Arbitros) o;
        return id == arbitros.id &&
                Objects.equals(dni, arbitros.dni) &&
                Objects.equals(pass, arbitros.pass) &&
                Objects.equals(nombre_completo, arbitros.nombre_completo) &&
                Objects.equals(foto, arbitros.foto) &&
                Objects.equals(email, arbitros.email) &&
                Objects.equals(fecha_nacimiento, arbitros.fecha_nacimiento) &&
                Objects.equals(provincia, arbitros.provincia) &&
                Objects.equals(localidad, arbitros.localidad) &&
                Objects.equals(cp, arbitros.cp) &&
                Objects.equals(categoria, arbitros.categoria) &&
                Objects.equals(telefono, arbitros.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dni, pass, nombre_completo, foto, email, fecha_nacimiento, provincia, localidad, cp, categoria, telefono);
    }

    public Arbitros(int id, String dni, String pass, String nombre_completo, String foto, String email, String fecha_nacimiento, String provincia, String localidad, String cp, String categoria, String telefono) {
        this.id = id;
        this.dni = dni;
        this.pass = pass;
        this.nombre_completo = nombre_completo;
        this.foto = foto;
        this.email = email;
        this.fecha_nacimiento = fecha_nacimiento;
        this.provincia = provincia;
        this.localidad = localidad;
        this.cp = cp;
        this.categoria = categoria;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Arbitro{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", pass='" + pass + '\'' +
                ", nombre_completo='" + nombre_completo + '\'' +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", provincia='" + provincia + '\'' +
                ", localidad='" + localidad + '\'' +
                ", cp='" + cp + '\'' +
                ", categoria='" + categoria + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
