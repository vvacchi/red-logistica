package com.uade.tpo.red_logistica.service;

import com.uade.tpo.red_logistica.dto.BranchAndBoundRequestDTO;
import com.uade.tpo.red_logistica.dto.RutaOptimaDTO;
import com.uade.tpo.red_logistica.repository.CentroDistribucionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BranchAndBoundService {

    private final CentroDistribucionRepository repo;

    private List<String> mejorRuta;
    private double mejorCosto;
    private String criterio;

    public BranchAndBoundService(CentroDistribucionRepository repo) {
        this.repo = repo;
    }

    public RutaOptimaDTO resolver(BranchAndBoundRequestDTO request) {

        this.criterio = request.getCriterio();
        this.mejorCosto = Double.MAX_VALUE;
        this.mejorRuta = new ArrayList<>();

        List<String> nodos = request.getNodos();
        int n = nodos.size();

        double[][] m = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (i == j) {
                    m[i][j] = 0;
                } else {
                    Double peso = getPeso(nodos.get(i), nodos.get(j));
                    m[i][j] = (peso == null) ? Double.MAX_VALUE : peso;
                }
            }
        }

        boolean[] visitados = new boolean[n];
        List<Integer> rutaActual = new ArrayList<>();

        visitados[0] = true;
        rutaActual.add(0);

        branch(m, visitados, rutaActual, 0, 0, nodos);

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
                mejorRuta = rutaActual.stream().map(i -> nodos.get(i)).toList();
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


    private Double getPeso(String origen, String destino) {
        return switch (criterio) {
            case "tiempo" -> repo.obtenerTiempo(origen, destino);
            case "costo" -> repo.obtenerCosto(origen, destino);
            default -> repo.obtenerDistancia(origen, destino);
        };
    }
}
