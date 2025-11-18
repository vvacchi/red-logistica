package com.uade.tpo.red_logistica.service;

import com.uade.tpo.red_logistica.service.BFSService;
import com.uade.tpo.red_logistica.dto.BacktrackingRequestDTO;
import com.uade.tpo.red_logistica.dto.RutaOptimaDTO;
import com.uade.tpo.red_logistica.model.nodes.CentroDistribucion;
import com.uade.tpo.red_logistica.repository.CentroDistribucionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BacktrackingService {

    private final CentroDistribucionRepository repo;
    private final BFSService bfsService;  

    private double mejorCosto;
    private List<String> mejorRuta;
    private String criterio;

    public BacktrackingService(CentroDistribucionRepository repo, BFSService bfsService) {
        this.repo = repo;
        this.bfsService = bfsService;
    }

    public RutaOptimaDTO resolver(BacktrackingRequestDTO request) {

        this.criterio = request.getCriterio();
        this.mejorCosto = Double.MAX_VALUE;
        this.mejorRuta = new ArrayList<>();

        // Obtener origen
        CentroDistribucion origen = repo.findByNombre(request.getOrigen());
        if (origen == null)
            throw new RuntimeException("El centro origen no existe: " + request.getOrigen());

        // Obtener destinos
        List<CentroDistribucion> destinos = new ArrayList<>();
        for (String nombre : request.getDestinos()) {
            CentroDistribucion c = repo.findByNombre(nombre);
            if (c == null)
                throw new RuntimeException("Destino inexistente: " + nombre);
            destinos.add(c);
        }

        // VALIDACIÓN DE CONECTIVIDAD
        for (String destino : request.getDestinos()) {
            if (!existeCamino(origen.getNombre(), destino)) {
                return new RutaOptimaDTO(
                        List.of(),
                        Double.POSITIVE_INFINITY,
                        criterio,
                        "BACKTRACKING - NODOS NO CONECTADOS"
                );
            }
        }

        Set<String> visitados = new HashSet<>();
        visitados.add(origen.getNombre());

        List<CentroDistribucion> rutaActual = new ArrayList<>();
        rutaActual.add(origen);

        backtracking(origen, destinos, rutaActual, visitados, 0);

        return new RutaOptimaDTO(mejorRuta, mejorCosto, criterio, "BACKTRACKING");
    }

    private void backtracking(CentroDistribucion actual,
                              List<CentroDistribucion> destinos,
                              List<CentroDistribucion> rutaActual,
                              Set<String> visitados,
                              double costoActual) {

        if (visitados.size() == destinos.size() + 1) {
            if (costoActual < mejorCosto) {
                mejorCosto = costoActual;
                mejorRuta = rutaActual.stream().map(CentroDistribucion::getNombre).toList();
            }
            return;
        }

        for (CentroDistribucion siguiente : destinos) {

            if (visitados.contains(siguiente.getNombre())) continue;

            Double peso = getPeso(actual.getNombre(), siguiente.getNombre());
            if (peso == null) continue;

            double nuevoCosto = costoActual + peso;

            if (nuevoCosto >= mejorCosto) continue;

            visitados.add(siguiente.getNombre());
            rutaActual.add(siguiente);

            backtracking(siguiente, destinos, rutaActual, visitados, nuevoCosto);

            visitados.remove(siguiente.getNombre());
            rutaActual.remove(rutaActual.size() - 1);
        }
    }

    private Double getPeso(String origen, String destino) {
        return switch (criterio) {
            case "tiempo" -> repo.obtenerTiempo(origen, destino);
            case "costo" -> repo.obtenerCosto(origen, destino);
            default -> repo.obtenerDistancia(origen, destino);
        };
    }

    private boolean existeCamino(String origen, String destino) {
        List<String> alcanzables = bfsService.recorridoBFS(origen);
        return alcanzables.contains(destino);
    }
}
