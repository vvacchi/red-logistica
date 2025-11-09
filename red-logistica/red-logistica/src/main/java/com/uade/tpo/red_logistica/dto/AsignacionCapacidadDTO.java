package com.uade.tpo.red_logistica.dto;

import java.util.List;

public class AsignacionCapacidadDTO {

    private String centro;
    private double capacidadMaxima;
    private List<ItemCliente> clientesSeleccionados;
    private double cargaTotal;
    private int cantidadClientes;

    public AsignacionCapacidadDTO(String centro, double capacidadMaxima,
                                  List<ItemCliente> clientesSeleccionados, double cargaTotal) {
        this.centro = centro;
        this.capacidadMaxima = capacidadMaxima;
        this.clientesSeleccionados = clientesSeleccionados;
        this.cargaTotal = cargaTotal;
        this.cantidadClientes = clientesSeleccionados.size();
    }

    // Getters y setters
    public String getCentro() { return centro; }
    public double getCapacidadMaxima() { return capacidadMaxima; }
    public List<ItemCliente> getClientesSeleccionados() { return clientesSeleccionados; }
    public double getCargaTotal() { return cargaTotal; }
    public int getCantidadClientes() { return cantidadClientes; }

    // Clase interna auxiliar
    public static class ItemCliente {
        private String nombre;
        private double demanda;

        public ItemCliente(String nombre, double demanda) {
            this.nombre = nombre;
            this.demanda = demanda;
        }

        public String getNombre() { return nombre; }
        public double getDemanda() { return demanda; }
    }
}
