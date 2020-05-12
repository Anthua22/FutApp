package com.example.futapp.ClasesPojos;

import java.io.Serializable;

public class Partidos implements Serializable {

    int idPartido;
    int jornada;
    int EquipoLocal;
    int EquipoVisitante;
    String provincia;
    String localidad;
    String categoria;
    int arbitroprincipal;
    int arbitrosecundario;
    int cronometrador;
    int tercer_arbitro;
    String direccion_encuentro;
    String fecha_encuentro;
    int disputado;
    String resultado;
    String acta;

    public Partidos() {
    }

    public Partidos(int idPartido, int jornada, int equipoLocal, int equipoVisitante, String provincia, String localidad, String categoria, int arbitroprincipal, String direccion_encuentro, String fecha_encuentro) {
        this.idPartido = idPartido;
        this.jornada = jornada;
        EquipoLocal = equipoLocal;
        EquipoVisitante = equipoVisitante;
        this.provincia = provincia;
        this.localidad = localidad;
        this.categoria = categoria;
        this.arbitroprincipal = arbitroprincipal;
        this.direccion_encuentro = direccion_encuentro;
        this.fecha_encuentro = fecha_encuentro;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public int getJornada() {
        return jornada;
    }

    public void setJornada(int jornada) {
        this.jornada = jornada;
    }

    public int getEquipoLocal() {
        return EquipoLocal;
    }

    public void setEquipoLocal(int equipoLocal) {
        EquipoLocal = equipoLocal;
    }

    public int getEquipoVisitante() {
        return EquipoVisitante;
    }

    public void setEquipoVisitante(int equipoVisitante) {
        EquipoVisitante = equipoVisitante;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getArbitroprincipal() {
        return arbitroprincipal;
    }

    public void setArbitroprincipal(int arbitroprincipal) {
        this.arbitroprincipal = arbitroprincipal;
    }

    public int getArbitrosecundario() {
        return arbitrosecundario;
    }

    public void setArbitrosecundario(int arbitrosecundario) {
        this.arbitrosecundario = arbitrosecundario;
    }

    public int getCronometrador() {
        return cronometrador;
    }

    public void setCronometrador(int cronometrador) {
        this.cronometrador = cronometrador;
    }

    public int getTercer_arbitro() {
        return tercer_arbitro;
    }

    public void setTercer_arbitro(int tercer_arbitro) {
        this.tercer_arbitro = tercer_arbitro;
    }

    public String getDireccion_encuentro() {
        return direccion_encuentro;
    }

    public void setDireccion_encuentro(String direccion_encuentro) {
        this.direccion_encuentro = direccion_encuentro;
    }

    public String getFecha_encuentro() {
        return fecha_encuentro;
    }

    public void setFecha_encuentro(String fecha_encuentro) {
        this.fecha_encuentro = fecha_encuentro;
    }

    public int getDisputado() {
        return disputado;
    }

    public void setDisputado(int disputado) {
        this.disputado = disputado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getActa() {
        return acta;
    }

    public void setActa(String acta) {
        this.acta = acta;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "idPartido=" + idPartido +
                ", jornada=" + jornada +
                ", EquipoLocal=" + EquipoLocal +
                ", EquipoVisitante=" + EquipoVisitante +
                ", provincia='" + provincia + '\'' +
                ", localidad='" + localidad + '\'' +
                ", categoria='" + categoria + '\'' +
                ", arbitroprincipal=" + arbitroprincipal +
                ", arbitrosecundario=" + arbitrosecundario +
                ", cronometrador=" + cronometrador +
                ", tercer_arbitro=" + tercer_arbitro +
                ", direccion_encuentro='" + direccion_encuentro + '\'' +
                ", fecha_encuentro='" + fecha_encuentro + '\'' +
                ", disputado=" + disputado +
                ", resultado='" + resultado + '\'' +
                ", acta='" + acta + '\'' +
                '}';
    }
}
