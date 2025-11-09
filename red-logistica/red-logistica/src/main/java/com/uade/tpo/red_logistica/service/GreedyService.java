package com.uade.tpo.red_logistica.service;

import com.uade.tpo.red_logistica.dto.ResultadoAsignacionDTO;
import com.uade.tpo.red_logistica.repository.CentroDistribucionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/*Realizar la verificacion y debuggeo , no funciona correctamente el codigo y la request devuelve error 500, 
 * "Records with more than one value cannot be converted without a mapper"*/
 
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

        List<String> clientes = repo.obtenerNombresClientes();
        List<ResultadoAsignacionDTO> resultados = new ArrayList<>();

        for (String cliente : clientes) {
            if (cliente == null || cliente.isBlank()) continue;

            List<Map<String, Object>> conexiones = repo.obtenerCentrosConPesosPorCliente(cliente, criterio);

            String mejorCentro = null;
            double mejorPeso = Double.POSITIVE_INFINITY;

            for (Map<String, Object> conexion : conexiones) {
                String nombreCentro = (String) conexion.get("nombreCentro");
                Object pesoObj = conexion.get("peso");
                if (nombreCentro == null || pesoObj == null) continue;

                double valor = (pesoObj instanceof Number)
                        ? ((Number) pesoObj).doubleValue()
                        : Double.parseDouble(pesoObj.toString());

                if (valor < mejorPeso) {
                    mejorPeso = valor;
                    mejorCentro = nombreCentro;
                }
            }

            if (mejorCentro != null) {
                resultados.add(new ResultadoAsignacionDTO(cliente, mejorCentro, mejorPeso));
            }
        }

        return resultados;
    }
}
