package com.uade.tpo.red_logistica.dto;

public class ConexionDTO {
    private String nombreCentro;
    private double peso;

    public ConexionDTO(String nombreCentro, double peso) {
        this.nombreCentro = nombreCentro;
        this.peso = peso;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public double getPeso() {
        return peso;
    }
}
