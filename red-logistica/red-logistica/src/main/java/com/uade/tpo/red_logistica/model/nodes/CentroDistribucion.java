package com.uade.tpo.red_logistica.model.nodes;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import com.uade.tpo.red_logistica.model.relationships.Ruta; 

import java.util.List;

@Node("CentroDistribucion")
public class CentroDistribucion {

    @Id
    private Long id;
    private String nombre;
    private String ubicacion;
    private int capacidad;

    @Relationship(type = "CONECTA_CON")
    private List<Ruta> conexiones;

    public CentroDistribucion() {}

    public CentroDistribucion(Long id, String nombre, String ubicacion, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
    }

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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Ruta> getConexiones() {
        return conexiones;
    }

    public void setConexiones(List<Ruta> conexiones) {
        this.conexiones = conexiones;
    }

    // getters y setters
}
