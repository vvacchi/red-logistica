import React, { useState } from "react";
import { calcularDijkstra } from "../../api/algorithmsApi.js";

export default function DijkstraComponent() {
  const [origen, setOrigen] = useState("");
  const [destino, setDestino] = useState("");
  const [criterio, setCriterio] = useState("distancia");
  const [resultado, setResultado] = useState(null);

  const ejecutar = async () => {
    const data = await calcularDijkstra(origen, destino, criterio);
    setResultado(data);
  };

  return (
    <div className="card">
      <h2>Dijkstra</h2>

      <div className="form-group">
        <label>Origen</label>
        <input value={origen} onChange={e => setOrigen(e.target.value)} />
      </div>

      <div className="form-group">
        <label>Destino</label>
        <input value={destino} onChange={e => setDestino(e.target.value)} />
      </div>

      <div className="form-group">
        <label>Criterio</label>
        <select value={criterio} onChange={e => setCriterio(e.target.value)}>
          <option value="distancia">Distancia</option>
          <option value="tiempo">Tiempo</option>
          <option value="costo">Costo</option>
        </select>
      </div>

      <button className="button-primary" onClick={ejecutar}>
        Ejecutar
      </button>

      {resultado && (
        <div className="result-container">
          <h3>Resultado</h3>
          <p><strong>Camino:</strong> {resultado.camino.join(" → ")}</p>
          <p><strong>Total:</strong> {Number(resultado.distanciaTotal ?? 0).toFixed(2)}</p>
          <p><strong>Tiempo:</strong> {resultado.tiempoEjecucionMs} ms</p>
        </div>
      )}
    </div>
  );
}
