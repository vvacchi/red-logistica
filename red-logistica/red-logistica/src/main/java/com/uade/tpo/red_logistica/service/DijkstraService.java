package com.uade.tpo.red_logistica.service;

import com.uade.tpo.red_logistica.model.nodes.CentroDistribucion;
import com.uade.tpo.red_logistica.repository.CentroDistribucionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DijkstraService {

    private final CentroDistribucionRepository repo;

    public DijkstraService(CentroDistribucionRepository repo) {
        this.repo = repo;
    }

    public Map<String, Object> calcularCaminoMinimo(String origen, String destino, String peso) {
        long inicio = System.nanoTime();
        String criterio = (peso == null) ? "distancia" : peso.toLowerCase(Locale.ROOT);

        List<CentroDistribucion> todos = repo.obtenerTodos();
        Set<String> nombres = new HashSet<>();
        for (CentroDistribucion c : todos) nombres.add(c.getNombre());

        if (!nombres.contains(origen) || !nombres.contains(destino)) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("camino", Collections.emptyList());
            error.put("distanciaTotal", Double.POSITIVE_INFINITY);
            error.put("error", "Origen o destino inexistente. Origen=" + origen + ", Destino=" + destino);
            error.put("tiempoEjecucionMs", (System.nanoTime() - inicio) / 1_000_000.0);
            return error;
        }

        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (String n : nombres) dist.put(n, Double.POSITIVE_INFINITY);
        dist.put(origen, 0.0);
        pq.add(origen);

        while (!pq.isEmpty()) {
            String u = pq.poll();
            if (!visitados.add(u)) continue;
            if (u.equals(destino)) break;

            List<Map<String, Object>> conexiones = repo.obtenerConexionesConPeso(u, criterio);

            for (Map<String, Object> conexion : conexiones) {
                String v = (String) conexion.get("destino");
                Object pesoObj = conexion.get("peso");
                if (v == null || pesoObj == null) continue;

                double w = (pesoObj instanceof Number)
                        ? ((Number) pesoObj).doubleValue()
                        : Double.parseDouble(pesoObj.toString());
                if (w < 0) continue;

                double nd = dist.get(u) + w;
                if (nd < dist.get(v)) {
                    dist.put(v, nd);
                    prev.put(v, u);
                    pq.remove(v);
                    pq.add(v);
                }
            }
        }

        List<String> camino = new ArrayList<>();
        if (dist.get(destino).isInfinite()) {
            Map<String, Object> res = new LinkedHashMap<>();
            res.put("camino", Collections.emptyList());
            res.put("distanciaTotal", Double.POSITIVE_INFINITY);
            res.put("mensaje", "No existe camino entre " + origen + " y " + destino + " para el criterio: " + criterio);
            res.put("tiempoEjecucionMs", (System.nanoTime() - inicio) / 1_000_000.0);
            return res;
        } else {
            String cur = destino;
            while (cur != null) {
                camino.add(cur);
                cur = prev.get(cur);
            }
            Collections.reverse(camino);
        }

        long fin = System.nanoTime();
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("camino", camino);
        out.put("criterio", criterio);
        out.put("distanciaTotal", dist.get(destino));
        out.put("tiempoEjecucionMs", (fin - inicio) / 1_000_000.0);
        return out;
    }
}
