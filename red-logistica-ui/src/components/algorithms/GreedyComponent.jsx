import { useState } from "react";
import { useApi } from "../../hooks/useApi.js";
import { API_URL } from "../../api/config.js"; 

export default function GreedyComponent() {
    const { request, data, loading, error } = useApi();
    const [criterio, setCriterio] = useState("distancia");

    const ejecutar = async () => {
        // Usamos la URL completa hacia el backend (puerto 8080)
        await request(`${API_URL}/greedy-asignacion?peso=${criterio}`);
    };

    return (
        <div>
            <h2>Greedy (Asignación de Clientes)</h2>

            <div className="form-group">
                <label>Criterio</label>
                <select value={criterio} onChange={(e) => setCriterio(e.target.value)}>
                    <option value="distancia">Distancia</option>
                    <option value="tiempo">Tiempo</option>
                    <option value="costo">Costo</option>
                </select>
            </div>

            <button className="button-primary" onClick={ejecutar} disabled={loading}>
                {loading ? "Procesando..." : "Ejecutar"}
            </button>

            <div className="result-container">
                <h3>Resultado</h3>

                {error && <p style={{ color: "red" }}>{error}</p>}

                {data && data.length > 0 && (
                    <ul>
                        {data.map((item, i) => (
                            <li key={i}>
                                <b>{item.centroAsignado} → {item.cliente}</b>  
                                {" — "}Peso: {item.peso}
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}