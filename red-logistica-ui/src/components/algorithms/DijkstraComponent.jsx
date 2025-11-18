import React, { useState } from "react";
import { calcularDijkstra } from "../../api/algorithmsApi";

export default function DijkstraComponent() {
  const [origen, setOrigen] = useState("");
  const [destino, setDestino] = useState("");
  const [criterio, setCriterio] = useState("distancia");
  const [resultado, setResultado] = useState(null);
  const [cargando, setCargando] = useState(false);

  const ejecutar = async () => {
    if (!origen || !destino) return;

    setCargando(true);
    try {
      const res = await calcularDijkstra(origen, destino, criterio);
      console.log("Respuesta Dijkstra:", res);
      setResultado(res);
    } catch (err) {
      console.error("Error ejecutando Dijkstra:", err);
    }
    setCargando(false);
  };

  const formatearCamino = () => {
    if (!resultado || !resultado.camino) return "Sin ruta disponible";
    if (resultado.camino.length === 0) return "Sin ruta disponible";
    return resultado.camino.join(" → ");
  };

  const formatearTotal = () => {
    if (!resultado || resultado.distanciaTotal === undefined) return "0";
    if (!isFinite(resultado.distanciaTotal)) return "∞ (no hay camino)";
    return resultado.distanciaTotal.toFixed(2);
  };

  return (
    <div className="card">
      <h2>Dijkstra</h2>

      <div className="form-group">
        <label>Origen</label>
        <input value={origen} onChange={(e) => setOrigen(e.target.value)} />
      </div>

      <div className="form-group">
        <label>Destino</label>
        <input value={destino} onChange={(e) => setDestino(e.target.value)} />
      </div>

      <div className="form-group">
        <label>Criterio</label>
        <select value={criterio} onChange={(e) => setCriterio(e.target.value)}>
          <option value="distancia">Distancia</option>
          <option value="tiempo">Tiempo</option>
          <option value="costo">Costo</option>
        </select>
      </div>

      <button className="button-primary" onClick={ejecutar} disabled={cargando}>
        {cargando ? "Calculando..." : "Ejecutar"}
      </button>

      {resultado && (
        <div className="result-container">
          <h3>Resultado</h3>
          <p><strong>Camino:</strong> {formatearCamino()}</p>
          <p><strong>Total:</strong> {formatearTotal()}</p>
          <p>
            <strong>Tiempo:</strong>{" "}
            {resultado.tiempoEjecucionMs?.toFixed(2)} ms
          </p>
        </div>
      )}
    </div>
  );
}
