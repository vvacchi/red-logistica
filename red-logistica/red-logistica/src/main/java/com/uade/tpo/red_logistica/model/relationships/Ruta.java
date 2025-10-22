package com.uade.tpo.red_logistica.model.relationships;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import com.uade.tpo.red_logistica.model.nodes.CentroDistribucion;

@RelationshipProperties
public class Ruta {

    @Id
    @GeneratedValue
    private Long id;

    private double distancia;
    private double costo;

    @TargetNode
    private CentroDistribucion destino;

    public Ruta() {}

    public Ruta(double distancia, double costo, CentroDistribucion destino) {
        this.distancia = distancia;
        this.costo = costo;
        this.destino = destino;
    }

    // Getters y Setters
    
    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public CentroDistribucion getDestino() {
        return destino;
    }

    public void setDestino(CentroDistribucion destino) {
        this.destino = destino;
    }
}
