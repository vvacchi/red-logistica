package com.uade.tpo.red_logistica.dto;

import java.util.List;

public class RutaOptimaDTO {

    private List<String> ruta;
    private double costoTotal;
    private String criterio;
    private String algoritmo;

    public RutaOptimaDTO() {
        // Constructor vacío necesario para JACKSON
    }

    public RutaOptimaDTO(List<String> ruta, double costoTotal, String criterio, String algoritmo) {
        this.ruta = ruta;
        this.costoTotal = costoTotal;
        this.criterio = criterio;
        this.algoritmo = algoritmo;
    }

    public List<String> getRuta() {
        return ruta;
    }

    public void setRuta(List<String> ruta) {
        this.ruta = ruta;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }
}
