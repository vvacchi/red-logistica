package com.uade.tpo.red_logistica.service;

import com.uade.tpo.red_logistica.dto.RutaDTO;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class MergeSortService {

    private final Driver driver;

    public MergeSortService(Driver driver) {
        this.driver = driver;
    }

    public List<RutaDTO> ordenarRutas(String criterio) {
        String peso = (criterio == null) ? "distancia" : criterio.toLowerCase(Locale.ROOT);

        String cypher = """
            MATCH (a:CentroDistribucion)-[r:CONECTA_CON]->(b:CentroDistribucion)
            RETURN a.nombre AS origen,
                   b.nombre AS destino,
                   CASE $peso
                        WHEN 'distancia' THEN r.distancia
                        WHEN 'tiempo' THEN r.tiempo
                        WHEN 'costo' THEN r.costo
                        ELSE r.distancia
                   END AS peso
            """;

        List<RutaDTO> rutas = new ArrayList<>();

        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            Result result = session.run(cypher, Map.of("peso", peso));

            
            for (var rec : result.list()) {
                String origen = rec.get("origen").asString();
                String destino = rec.get("destino").asString();
                double valor = rec.get("peso").asDouble();
                rutas.add(new RutaDTO(origen, destino, valor));
            }
        }

        return mergeSort(rutas);
    }

    private List<RutaDTO> mergeSort(List<RutaDTO> lista) {
        if (lista.size() <= 1) return lista;

        int medio = lista.size() / 2;
        List<RutaDTO> izquierda = mergeSort(new ArrayList<>(lista.subList(0, medio)));
        List<RutaDTO> derecha = mergeSort(new ArrayList<>(lista.subList(medio, lista.size())));

        return merge(izquierda, derecha);
    }

    private List<RutaDTO> merge(List<RutaDTO> izquierda, List<RutaDTO> derecha) {
        List<RutaDTO> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < izquierda.size() && j < derecha.size()) {
            if (izquierda.get(i).compareTo(derecha.get(j)) <= 0) {
                resultado.add(izquierda.get(i++));
            } else {
                resultado.add(derecha.get(j++));
            }
        }

        resultado.addAll(izquierda.subList(i, izquierda.size()));
        resultado.addAll(derecha.subList(j, derecha.size()));
        return resultado;
    }
}
