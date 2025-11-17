package com.uade.tpo.red_logistica.dto;

import java.util.List;

public class BacktrackingRequestDTO {
    private String origen;
    private List<String> destinos;
    private String criterio;

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public List<String> getDestinos() { return destinos; }
    public void setDestinos(List<String> destinos) { this.destinos = destinos; }

    public String getCriterio() { return criterio; }
    public void setCriterio(String criterio) { this.criterio = criterio; }
}
