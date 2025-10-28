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
    public List<ResultadoAsignacionDTO> asignarClientes(String peso) {
        String criterio = (peso == null) ? "distancia" : peso.toLowerCase(Locale.ROOT);

        List<String> clientes = repo.obtenerClientes();
        List<ResultadoAsignacionDTO> resultados = new ArrayList<>();

        for (String cliente : clientes) {
            List<Map<String, Object>> conexiones = repo.obtenerCentrosConPesosPorCliente(cliente, criterio);

            Map<String, Object> mejor = null;
            double mejorPeso = Double.POSITIVE_INFINITY;

            for (Map<String, Object> conexion : conexiones) {
                String centro = (String) conexion.get("nombreCentro");
                Object pesoObj = conexion.get("peso");
                if (pesoObj == null) continue;

                double valor = (pesoObj instanceof Number)
                        ? ((Number) pesoObj).doubleValue()
                        : Double.parseDouble(pesoObj.toString());

                if (valor < mejorPeso) {
                    mejorPeso = valor;
                    mejor = conexion;
                }
            }

            if (mejor != null) {
                resultados.add(new ResultadoAsignacionDTO(
                        cliente,
                        (String) mejor.get("nombreCentro"),
                        mejorPeso
                ));
            }
        }

        return resultados;
    }
}
