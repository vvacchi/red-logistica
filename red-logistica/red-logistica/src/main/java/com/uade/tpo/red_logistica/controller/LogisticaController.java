package com.uade.tpo.red_logistica.controller;

import com.uade.tpo.red_logistica.service.LogisticaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistica")
public class LogisticaController {

    private final LogisticaService service;

    public LogisticaController(LogisticaService service) {
        this.service = service;
    }

    @GetMapping("/bfs/{origen}")
    public List<String> recorrerBFS(@PathVariable String origen) {
        return service.recorridoBFS(origen);
    }
}
