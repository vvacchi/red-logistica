package com.uade.tpo.red_logistica.controller;

import com.uade.tpo.red_logistica.service.BFSService;
import com.uade.tpo.red_logistica.service.DFSService;
import com.uade.tpo.red_logistica.service.DijkstraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logistica")
public class LogisticaController {

    private final BFSService bfsService;
    private final DFSService dfsService;
    private final DijkstraService dijkstraService;

    public LogisticaController(BFSService bfsService,
                               DFSService dfsService,
                               DijkstraService dijkstraService) {
        this.bfsService = bfsService;
        this.dfsService = dfsService;
        this.dijkstraService = dijkstraService;
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
}
