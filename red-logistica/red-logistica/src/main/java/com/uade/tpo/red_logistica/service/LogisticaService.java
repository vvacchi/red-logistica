package com.uade.tpo.red_logistica.service;

import com.uade.tpo.red_logistica.model.nodes.CentroDistribucion;
import com.uade.tpo.red_logistica.repository.CentroDistribucionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LogisticaService {

    private final CentroDistribucionRepository repo;

    public LogisticaService(CentroDistribucionRepository repo) {
        this.repo = repo;
    }

    public List<String> recorridoBFS(String origen) {
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();
        List<String> resultado = new ArrayList<>();

        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            resultado.add(actual);

            List<CentroDistribucion> conexiones = repo.obtenerConexiones(actual);
            for (CentroDistribucion c : conexiones) {
                if (!visitados.contains(c.getNombre())) {
                    cola.add(c.getNombre());
                    visitados.add(c.getNombre());
                }
            }
        }
        return resultado;
    }
}
