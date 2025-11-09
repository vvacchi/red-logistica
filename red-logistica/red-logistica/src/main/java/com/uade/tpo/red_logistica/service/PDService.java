package com.uade.tpo.red_logistica.service;

import com.uade.tpo.red_logistica.dto.AsignacionCapacidadDTO;
import com.uade.tpo.red_logistica.dto.AsignacionCapacidadDTO.ItemCliente;
import com.uade.tpo.red_logistica.repository.CentroDistribucionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PDService {

    private final CentroDistribucionRepository repo;

    public PDService(CentroDistribucionRepository repo) {
        this.repo = repo;
    }

    public AsignacionCapacidadDTO resolverAsignacion(String centro, double capacidad) {

        
        List<String> nombresClientes = repo.obtenerNombresClientes();

        List<ItemCliente> items = new ArrayList<>();
        for (String nombre : nombresClientes) {
            if (nombre == null || nombre.isBlank()) continue;

            double demanda = Math.random() * 30 + 10; // simula requerimiento entre 10 y 40
            items.add(new ItemCliente(nombre, demanda));
        }

        int n = items.size();
        double[][] dp = new double[n + 1][(int) capacidad + 1];

        for (int i = 1; i <= n; i++) {
            double peso = items.get(i - 1).getDemanda();
            for (int w = 1; w <= capacidad; w++) {
                if (peso <= w)
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][(int) (w - peso)] + peso);
                else
                    dp[i][w] = dp[i - 1][w];
            }
        }

        List<ItemCliente> seleccionados = new ArrayList<>();
        int w = (int) capacidad;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                ItemCliente it = items.get(i - 1);
                seleccionados.add(it);
                w -= it.getDemanda();
            }
        }

        double cargaTotal = seleccionados.stream().mapToDouble(ItemCliente::getDemanda).sum();
        return new AsignacionCapacidadDTO(centro, capacidad, seleccionados, cargaTotal);
    }
}
