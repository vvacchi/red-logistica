package com.uade.tpo.red_logistica.dto;

public class ResultadoAsignacionDTO {
    private String cliente;
    private String centroAsignado;
    private double peso;

    public ResultadoAsignacionDTO(String cliente, String centroAsignado, double peso) {
        this.cliente = cliente;
        this.centroAsignado = centroAsignado;
        this.peso = peso;
    }

    public String getCliente() { return cliente; }
    public String getCentroAsignado() { return centroAsignado; }
    public double getPeso() { return peso; }
}