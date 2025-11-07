package com.uade.tpo.red_logistica.dto;

public class RutaDTO implements Comparable<RutaDTO> {
    private String origen;
    private String destino;
    private double peso;

    public RutaDTO(String origen, String destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public double getPeso() { return peso; }

    @Override
    public int compareTo(RutaDTO otra) {
        return Double.compare(this.peso, otra.peso);
    }
}