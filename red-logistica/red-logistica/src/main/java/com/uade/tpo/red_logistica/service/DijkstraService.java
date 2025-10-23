package com.uade.tpo.red_logistica.service;

import com.uade.tpo.red_logistica.dto.ConexionDTO;
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

    /**
     * Calcula el camino mínimo entre dos centros de distribución utilizando Dijkstra.
     *
     * @param origen  nombre del centro inicial
     * @param destino nombre del centro destino
     * @param peso    "distancia", "tiempo" o "costo" (case-insensitive)
     */
    public Map<String, Object> calcularCaminoMinimo(String origen, String destino, String peso) {
        long inicio = System.nanoTime();

        // Normalizar el criterio de peso
        String criterio = (peso == null) ? "distancia" : peso.toLowerCase(Locale.ROOT);

        // Validación de nodos existentes
        List<CentroDistribucion> todos = repo.obtenerTodos();
        Set<String> nombres = new HashSet<>();
        for (CentroDistribucion c : todos) {
            nombres.add(c.getNombre());
        }

        if (!nombres.contains(origen) || !nombres.contains(destino)) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("camino", Collections.emptyList());
            error.put("distanciaTotal", Double.POSITIVE_INFINITY);
            error.put("error",
                    "Origen o destino inexistente en la base. Origen=" + origen + ", Destino=" + destino);
            error.put("tiempoEjecucionMs", (System.nanoTime() - inicio) / 1_000_000.0);
            return error;
        }

        // Estructuras de Dijkstra
        Map<String, Double> distancias = new HashMap<>();
        Map<String, String> previos = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        PriorityQueue<String> cola = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));

        // Inicializar distancias a infinito
        for (String nombre : nombres) {
            distancias.put(nombre, Double.POSITIVE_INFINITY);
        }
        distancias.put(origen, 0.0);
        cola.add(origen);

        // Dijkstra
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            if (!visitados.add(actual)) continue; // si ya fue visitado, seguir

            // Si querés cortar temprano cuando llegás al destino:
            if (actual.equals(destino)) break;

            List<ConexionDTO> conexiones = repo.obtenerConexionesConPeso(actual, criterio);
            for (ConexionDTO conexion : conexiones) {
                String v = conexion.getDestino();
                Double w = conexion.getPeso();
                if (w == null || w < 0) continue; // ignorar pesos inválidos/negativos

                double nuevaDist = distancias.get(actual) + w;
                if (nuevaDist < distancias.get(v)) {
                    distancias.put(v, nuevaDist);
                    previos.put(v, actual);
                    // actualizar prioridad en la cola
                    cola.remove(v);
                    cola.add(v);
                }
            }
        }

        // Reconstrucción del camino
        List<String> camino = new ArrayList<>();
        if (distancias.get(destino).isInfinite()) {
            // Inalcanzable
            long fin = System.nanoTime();
            Map<String, Object> res = new LinkedHashMap<>();
            res.put("camino", Collections.emptyList());
            res.put("distanciaTotal", Double.POSITIVE_INFINITY);
            res.put("mensaje", "No existe camino entre " + origen + " y " + destino + " para el criterio: " + criterio);
            res.put("tiempoEjecucionMs", (fin - inicio) / 1_000_000.0);
            return res;
        } else {
            String cur = destino;
            while (cur != null) {
                camino.add(cur);
                cur = previos.get(cur);
            }
            Collections.reverse(camino);
        }

        long fin = System.nanoTime();
        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("camino", camino);
        resultado.put("criterio", criterio);
        resultado.put("distanciaTotal", distancias.get(destino));
        resultado.put("tiempoEjecucionMs", (fin - inicio) / 1_000_000.0);

        return resultado;
        }
}
