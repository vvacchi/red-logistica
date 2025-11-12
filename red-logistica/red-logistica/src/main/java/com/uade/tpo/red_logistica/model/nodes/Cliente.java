package com.uade.tpo.red_logistica.model.nodes;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import com.uade.tpo.red_logistica.model.relationships.AtendidoPor; 
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Cliente")
public class Cliente {
    @Id
    private Long id;
    
    @Property("nombre")
    private String nombre;

    private String zona;
    private Integer demanda;

    @Relationship(type = "ATENDIDO_POR")
    private AtendidoPor centro;

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

    public void setDemanda(Integer demanda) {
        this.demanda = demanda;
    }

    public AtendidoPor getCentro() {
        return centro;
    }

    public void setCentro(AtendidoPor centro) {
        this.centro = centro;
    }
}
