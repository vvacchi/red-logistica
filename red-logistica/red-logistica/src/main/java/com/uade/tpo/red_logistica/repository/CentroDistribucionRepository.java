package com.uade.tpo.red_logistica.repository;

import com.uade.tpo.red_logistica.model.nodes.CentroDistribucion;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CentroDistribucionRepository extends Neo4jRepository<CentroDistribucion, Long> {

    @Query("MATCH (c:CentroDistribucion) RETURN c")
    List<CentroDistribucion> obtenerTodos();

    @Query("MATCH (c:CentroDistribucion {nombre: $nombre})-[:CONECTA_CON]->(dest) RETURN dest")
    List<CentroDistribucion> obtenerConexiones(String nombre);

    // 🔹 Solución: devolver un solo valor (mapa literal con destino y peso)
    @Query("""
        MATCH (a:CentroDistribucion {nombre: $nombre})-[r:CONECTA_CON]->(b:CentroDistribucion)
        RETURN {destino: b.nombre,
                peso: CASE $peso
                        WHEN 'distancia' THEN r.distancia
                        WHEN 'tiempo' THEN r.tiempo
                        WHEN 'costo' THEN r.costo
                        ELSE r.distancia
                      END} AS conexion
        """)
    List<Map<String, Object>> obtenerConexionesConPeso(String nombre, String peso);
}
