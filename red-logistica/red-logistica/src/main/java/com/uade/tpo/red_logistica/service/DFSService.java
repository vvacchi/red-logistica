package com.uade.tpo.red_logistica.service;

import com.uade.tpo.red_logistica.model.nodes.CentroDistribucion;
import com.uade.tpo.red_logistica.repository.CentroDistribucionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DFSService {

    private final CentroDistribucionRepository repo;

    public DFSService(CentroDistribucionRepository repo) {
        this.repo = repo;
    }

    /**
     * Recorre el grafo en profundidad (DFS) desde el centro origen.
     * @param origen nombre del centro de distribución inicial
     * @return lista ordenada de nodos visitados
     */
    public List<String> recorridoDFS(String origen) {
        Set<String> visitados = new HashSet<>();
        List<String> resultado = new ArrayList<>();
        dfsRecursivo(origen, visitados, resultado);
        return resultado;
    }

    private void dfsRecursivo(String actual, Set<String> visitados, List<String> resultado) {
        visitados.add(actual);
        resultado.add(actual);

        List<CentroDistribucion> conexiones = repo.obtenerConexiones(actual);
        for (CentroDistribucion c : conexiones) {
            String nombre = c.getNombre();
            if (!visitados.contains(nombre)) {
                dfsRecursivo(nombre, visitados, resultado);
            }
        }
    }
}
