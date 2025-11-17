package com.uade.tpo.red_logistica.dto;

import java.util.List;

public class BranchAndBoundRequestDTO {

    private List<String> nodos;
    private String criterio;

    public List<String> getNodos() { return nodos; }
    public void setNodos(List<String> nodos) { this.nodos = nodos; }

    public String getCriterio() { return criterio; }
    public void setCriterio(String criterio) { this.criterio = criterio; }
}
