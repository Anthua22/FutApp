package com.example.futapp.ClasesPojos;

import java.io.Serializable;

public class Staffs implements Serializable {
    int id;
    String cargo;
    String fecha_nacimiento;
    String dni;
    String nombre_completo;
    int equipo;

    public Staffs() {
    }

    public Staffs(int id, String cargo, String fecha_nacimiento, String dni, int equipo, String nombre_completo) {
        this.id = id;
        this.cargo = cargo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.dni = dni;
        this.nombre_completo = nombre_completo;
        this.equipo = equipo;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
        return "Staffs{" +
                "id=" + id +
                ", cargo='" + cargo + '\'' +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", dni='" + dni + '\'' +
                ", nombre_completo='" + nombre_completo + '\'' +
                ", equipo=" + equipo +
                '}';
    }
}
