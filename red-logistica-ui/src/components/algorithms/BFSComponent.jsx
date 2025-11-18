import React, { useState } from "react";
import { ejecutarBFS } from "../../api/algorithmsApi";

export default function BFSComponent() {
  const [origen, setOrigen] = useState("");
  const [resultado, setResultado] = useState(null);
  const [cargando, setCargando] = useState(false);

  const ejecutar = async () => {
    if (!origen.trim()) return;

    setCargando(true);
    try {
      const res = await ejecutarBFS(origen);
      setResultado(res);
    } catch (e) {
      console.error(e);
    }
    setCargando(false);
  };

  return (
    <div className="card">
      <h2>BFS (Recorrido en Amplitud)</h2>

      <div className="form-group">
        <label>Origen</label>
        <input
          value={origen}
          onChange={(e) => setOrigen(e.target.value)}
          placeholder="Ej: Centro Sur"
        />
      </div>

      <button className="button-primary" onClick={ejecutar} disabled={cargando}>
        {cargando ? "Ejecutando..." : "Ejecutar"}
      </button>

      {resultado && (
        <div className="result-container">
          <h3>Resultado</h3>
          <p>
            <strong>Recorrido:</strong>{" "}
            {resultado.length > 0
              ? resultado.join(" → ")
              : "Sin nodos alcanzables"}
          </p>
        </div>
      )}
    </div>
  );
}
