package com.uade.tpo.red_logistica.model.nodes;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Cliente")
public class Cliente {
    @Id
    private Long id;
    private String nombre;
    private String zona;
    private int demanda;

    @Relationship(type = "ATENDIDO_POR")
    private CentroDistribucion centro;

    public Cliente() {}
    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getDemanda() {
        return demanda;
    }

    public void setDemanda(int demanda) {
        this.demanda = demanda;
    }

    public CentroDistribucion getCentro() {
        return centro;
    }

    public void setCentro(CentroDistribucion centro) {
        this.centro = centro;
    }
}
