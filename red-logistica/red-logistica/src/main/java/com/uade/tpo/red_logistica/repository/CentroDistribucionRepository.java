package com.uade.tpo.red_logistica.repository;

import com.uade.tpo.red_logistica.dto.*;
import com.uade.tpo.red_logistica.model.nodes.CentroDistribucion;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.uade.tpo.red_logistica.model.nodes.Cliente;
import com.uade.tpo.red_logistica.dto.ConexionDTO;


@Repository
public interface CentroDistribucionRepository extends Neo4jRepository<CentroDistribucion, Long> {

    CentroDistribucion findByNombre(String nombre);

    @Query("MATCH (c:CentroDistribucion) RETURN c")
    List<CentroDistribucion> obtenerTodos();

    @Query("MATCH (c:CentroDistribucion {nombre: $nombre})-[:CONECTA_CON]->(dest) RETURN dest")
    List<CentroDistribucion> obtenerConexiones(String nombre);

   @Query("""
        MATCH (a:CentroDistribucion {nombre: $nombre})-[r:CONECTA_CON]->(b:CentroDistribucion)
        RETURN {
            destino: b.nombre,
            peso: CASE $peso
                WHEN 'distancia' THEN r.distancia
                WHEN 'tiempo' THEN r.tiempo
                WHEN 'costo' THEN r.costo
                ELSE r.distancia
            END
        } AS data
        """)
    List<Map<String, Object>> obtenerConexionesConPeso(String nombre, String peso);

    @Query("MATCH (c:Cliente) RETURN c.nombre AS nombre")
    List<String> obtenerNombresClientes();

   @Query("""
        MATCH (cli:Cliente {nombre: $cliente})-[r:ATENDIDO_POR]->(centro:CentroDistribucion)
        RETURN {
            nombreCentro: centro.nombre,
            peso: CASE $peso
                WHEN 'distancia' THEN r.distancia
                WHEN 'tiempo' THEN r.tiempo
                WHEN 'costo' THEN r.costo
                ELSE r.distancia
            END
        } AS data
        """)
    List<Map<String, Object>> obtenerCentrosConPesosPorCliente(String cliente, String peso);

   @Query("""
        MATCH (o:CentroDistribucion {nombre: $origen}), (d:CentroDistribucion {nombre: $destino}) 
        MATCH p = shortestPath((o)-[:CONECTA_CON*]->(d)) 
        RETURN reduce(s = 0.0, r IN relationships(p) | s + r.distancia)
        """)
    Double obtenerDistancia(String origen, String destino);

    @Query("""
        MATCH (o:CentroDistribucion {nombre: $origen}), (d:CentroDistribucion {nombre: $destino}) 
        MATCH p = shortestPath((o)-[:CONECTA_CON*]->(d)) 
        RETURN reduce(s = 0.0, r IN relationships(p) | s + r.tiempo)
        """)
    Double obtenerTiempo(String origen, String destino);

    @Query("""
        MATCH (o:CentroDistribucion {nombre: $origen}), (d:CentroDistribucion {nombre: $destino}) 
        MATCH p = shortestPath((o)-[:CONECTA_CON*]->(d)) 
        RETURN reduce(s = 0.0, r IN relationships(p) | s + r.costo)
        """)
    Double obtenerCosto(String origen, String destino);

    @Query("""
        MATCH (cli:Cliente {nombre: $cliente})-[:ATENDIDO_POR]->(centro:CentroDistribucion)
        MATCH (centro)-[r:CONECTA_CON]->(otro:CentroDistribucion)
        RETURN 
            cli.nombre AS cliente,
            otro.nombre AS centroAsignado,
            CASE $criterio
                WHEN 'distancia' THEN r.distancia
                WHEN 'tiempo' THEN r.tiempo
                WHEN 'costo' THEN r.costo
                ELSE r.distancia
            END AS peso
    """)
    List<ResultadoAsignacionDTO> obtenerPesosPorClienteConGrafo(String cliente, String criterio);
    

}

