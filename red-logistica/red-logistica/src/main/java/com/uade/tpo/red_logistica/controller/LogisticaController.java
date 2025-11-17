package com.uade.tpo.red_logistica.controller;

import com.uade.tpo.red_logistica.service.*;

import com.uade.tpo.red_logistica.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logistica")
public class LogisticaController {

    private final BFSService bfsService;
    private final DFSService dfsService;
    private final DijkstraService dijkstraService;
    private final GreedyService greedyService;
    private final MergeSortService mergeSortService;
    private final PDService pdService;
    private final BacktrackingService backtrackingService;
    private final BranchAndBoundService branchAndBoundService;

    public LogisticaController(BFSService bfsService,
                               DFSService dfsService,
                               DijkstraService dijkstraService,
                               GreedyService greedyService,
                               MergeSortService mergeSortService,
                               PDService pdService,
                               BacktrackingService backtrackingService,
                               BranchAndBoundService branchAndBoundService) {
        this.bfsService = bfsService;
        this.dfsService = dfsService;
        this.dijkstraService = dijkstraService;
        this.greedyService = greedyService;
        this.mergeSortService = mergeSortService;
        this.pdService = pdService;
        this.backtrackingService = backtrackingService;
        this.branchAndBoundService = branchAndBoundService;
    }

    // --- BFS ---
    @GetMapping("/bfs/{origen}")
    public List<String> recorrerBFS(@PathVariable String origen) {
        return bfsService.recorridoBFS(origen);
    }

    // --- DFS ---
    @GetMapping("/dfs/{origen}")
    public List<String> recorrerDFS(@PathVariable String origen) {
        return dfsService.recorridoDFS(origen);
    }

    // --- Dijkstra ---
    @GetMapping("/dijkstra/{origen}/{destino}")
    public Map<String, Object> calcularCaminoMinimo(
            @PathVariable String origen,
            @PathVariable String destino,
            @RequestParam(defaultValue = "distancia") String peso) {

        return dijkstraService.calcularCaminoMinimo(origen, destino, peso);
    }
    @GetMapping("/greedy-asignacion")
    public List<ResultadoAsignacionDTO> asignacionGreedy(
            @RequestParam(defaultValue = "distancia") String peso) {
        return greedyService.asignarClientes(peso);
    }
    @GetMapping("/ordenar-rutas")
    public List<RutaDTO> ordenarRutas(@RequestParam(defaultValue = "distancia") String criterio) {
        return mergeSortService.ordenarRutas(criterio);
    }
    @PostMapping("/pd-capacidad")
    public AsignacionCapacidadDTO asignarCapacidad(@RequestParam String centro, @RequestParam double capacidad) {
        return pdService.resolverAsignacion(centro, capacidad);
    }
    @PostMapping("/backtracking")
    public RutaOptimaDTO backtracking(@RequestBody BacktrackingRequestDTO dto) {
        return backtrackingService.resolver(dto);
    }
    @PostMapping("/branch-and-bound")
    public RutaOptimaDTO resolverBranch(@RequestBody BranchAndBoundRequestDTO dto) {
        return branchAndBoundService.resolver(dto);
    }

}
