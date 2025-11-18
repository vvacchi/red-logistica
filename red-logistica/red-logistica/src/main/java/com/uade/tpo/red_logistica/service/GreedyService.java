package com.uade.tpo.red_logistica.service;

import com.uade.tpo.red_logistica.dto.ResultadoAsignacionDTO;
import com.uade.tpo.red_logistica.repository.CentroDistribucionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GreedyService {

    private final CentroDistribucionRepository repo;

    public GreedyService(CentroDistribucionRepository repo) {
        this.repo = repo;
    }

    /**
     * Asigna cada cliente al centro más cercano o económico según el criterio elegido.
     */
    public List<ResultadoAsignacionDTO> asignarClientes(String criterio) {

        criterio = criterio == null ? "distancia" : criterio.toLowerCase();
        List<String> clientes = repo.obtenerNombresClientes();
        List<ResultadoAsignacionDTO> resultados = new ArrayList<>();

        for (String cliente : clientes) {

            List<ResultadoAsignacionDTO> filas =
                    repo.obtenerPesosPorClienteConGrafo(cliente, criterio);

            ResultadoAsignacionDTO mejor = null;

            for (ResultadoAsignacionDTO fila : filas) {
                if (mejor == null || fila.getPeso() < mejor.getPeso()) {
                    mejor = fila;
                }
            }

            if (mejor != null) {
                resultados.add(mejor);
            }
        }

        return resultados;
    }


}
