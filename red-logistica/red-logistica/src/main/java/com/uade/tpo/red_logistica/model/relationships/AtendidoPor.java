package com.uade.tpo.red_logistica.model.relationships;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import com.uade.tpo.red_logistica.model.nodes.CentroDistribucion;

@RelationshipProperties("ATENDIDO_POR") // Especifica el tipo de relación
public class AtendidoPor {

    @Id
    @GeneratedValue
    private Long id;

    private double distancia;
    private double tiempo;
    private double costo;

    @TargetNode
    private CentroDistribucion centroDistribucion; // El nodo destino de esta relación

    public AtendidoPor() {}

    public AtendidoPor(double distancia, double tiempo, double costo, CentroDistribucion centroDistribucion) {
        this.distancia = distancia;
        this.tiempo = tiempo;
        this.costo = costo;
        this.centroDistribucion = centroDistribucion;
    }

    // Getters y Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public CentroDistribucion getCentroDistribucion() {
        return centroDistribucion;
    }

    public void setCentroDistribucion(CentroDistribucion centroDistribucion) {
        this.centroDistribucion = centroDistribucion;
    }
}
