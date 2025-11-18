package com.uade.tpo.red_logistica.service;

import com.uade.tpo.red_logistica.service.BFSService; 
import com.uade.tpo.red_logistica.dto.BranchAndBoundRequestDTO;
import com.uade.tpo.red_logistica.dto.RutaOptimaDTO;
import com.uade.tpo.red_logistica.repository.CentroDistribucionRepository;
import org.springframework.stereotype.Service;
import com.uade.tpo.red_logistica.service.DijkstraService;

import java.util.*;

@Service
public class BranchAndBoundService {

    private final CentroDistribucionRepository repo;
    private final DijkstraService dijkstraService;

    private List<String> mejorRuta;
    private double mejorCosto;
    private String criterio;

    public BranchAndBoundService(CentroDistribucionRepository repo,
                                 DijkstraService dijkstraService) {
        this.repo = repo;
        this.dijkstraService = dijkstraService;
    }

    public RutaOptimaDTO resolver(BranchAndBoundRequestDTO request) {

        this.criterio = request.getCriterio();
        this.mejorCosto = Double.MAX_VALUE;
        this.mejorRuta = new ArrayList<>();

        List<String> nodos = request.getNodos();
        int n = nodos.size();

        // MATRIZ DE PESOS USANDO DIJKSTRA
        double[][] m = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (i == j) {
                    m[i][j] = 0;
                } else {
                    try {
                        Map<String, Object> result = dijkstraService.calcularCaminoMinimo(
                                nodos.get(i), nodos.get(j), criterio);

                        if (result.get("distanciaTotal") instanceof Number num) {
                            m[i][j] = num.doubleValue();
                        } else {
                            m[i][j] = Double.MAX_VALUE;
                        }

                    } catch (Exception e) {
                        m[i][j] = Double.MAX_VALUE; // No hay camino
                    }
                }
            }
        }

        boolean[] visitados = new boolean[n];
        List<Integer> rutaActual = new ArrayList<>();

        visitados[0] = true;
        rutaActual.add(0);

        branch(m, visitados, rutaActual, 0, 0, nodos);

        if (mejorRuta.isEmpty())
            return new RutaOptimaDTO(List.of(), Double.POSITIVE_INFINITY, criterio,
                    "BRANCH_AND_BOUND - NODOS NO CONECTADOS");

        return new RutaOptimaDTO(mejorRuta, mejorCosto, criterio, "BRANCH_AND_BOUND");
    }


    private void branch(double[][] m,
                        boolean[] visitados,
                        List<Integer> rutaActual,
                        int ultimo,
                        double costoActual,
                        List<String> nodos) {

        int n = m.length;

        if (rutaActual.size() == n) {
            if (costoActual < mejorCosto) {
                mejorCosto = costoActual;
                mejorRuta = rutaActual.stream().map(nodos::get).toList();
            }
            return;
        }

        for (int i = 0; i < n; i++) {

            if (!visitados[i] && m[ultimo][i] != Double.MAX_VALUE) {

                double nuevoCosto = costoActual + m[ultimo][i];

                double cota = nuevoCosto + cotaInferior(m, visitados);

                if (cota >= mejorCosto) continue;

                visitados[i] = true;
                rutaActual.add(i);

                branch(m, visitados, rutaActual, i, nuevoCosto, nodos);

                visitados[i] = false;
                rutaActual.remove(rutaActual.size() - 1);
            }
        }
    }

    private double cotaInferior(double[][] m, boolean[] visitados) {
        double cota = 0;

        for (int i = 0; i < m.length; i++) {
            if (!visitados[i]) {
                double min = Double.MAX_VALUE;
                for (int j = 0; j < m.length; j++) {
                    if (i != j && m[i][j] < min) {
                        min = m[i][j];
                    }
                }
                cota += min;
            }
        }
        return cota;
    }
}

