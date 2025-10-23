package com.uade.tpo.red_logistica.controller;

import com.uade.tpo.red_logistica.service.BFSService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistica")
public class LogisticaController {

    private final BFSService service;

    public LogisticaController(BFSService service) {
        this.service = service;
    }

    @GetMapping("/bfs/{origen}")
    public List<String> recorrerBFS(@PathVariable String origen) {
        return service.recorridoBFS(origen);
    }
}
