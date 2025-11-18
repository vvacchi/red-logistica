import { useState } from "react";
import { useApi } from "../../hooks/useApi.js";
import { API_URL } from "../../api/config.js"; 

export default function MergeSortComponent() {
    const { request, data, loading, error } = useApi();
    const [criterio, setCriterio] = useState("distancia");

    const ejecutar = async () => {
        // Concatenamos la URL base correctamente
        await request(`${API_URL}/ordenar-rutas?criterio=${criterio}`);
    };

    return (
        <div>
            <h2>MergeSort (Ordenamiento de Rutas)</h2>

            <div className="form-group">
                <label>Criterio</label>
                <select value={criterio} onChange={(e) => setCriterio(e.target.value)}>
                    <option value="distancia">Distancia</option>
                    <option value="tiempo">Tiempo</option>
                    <option value="costo">Costo</option>
                </select>
            </div>

            <button onClick={ejecutar} className="button-primary" disabled={loading}>
                {loading ? "Ordenando..." : "Ejecutar"}
            </button>

            <div className="result-container">
                <h3>Resultado</h3>

                {error && <p style={{ color: "red" }}>{error}</p>}

                {data && data.length > 0 && (
                    <ul>
                        {data.map((r, i) => (
                            <li key={i}>
                                {/* Usamos las propiedades exactas del JSON: origen, destino, peso */}
                                <b>{r.origen} → {r.destino}</b> 
                                {" — "} 
                                {/* Mostramos el nombre del criterio elegido, pero el valor siempre viene en r.peso */}
                                {criterio.charAt(0).toUpperCase() + criterio.slice(1)}: {r.peso}
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}