import { useState } from "react";
import { useApi } from "../../hooks/useApi";

export default function MergeSortComponent() {
    const { request, data, loading, error } = useApi();
    const [criterio, setCriterio] = useState("distancia");

    const ejecutar = async () => {
        await request(`/ordenar-rutas?criterio=${criterio}`);
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

                {data && (
                    <ul>
                        {data.map((r, i) => (
                            <li key={i}>{r.origen} → {r.destino} — {criterio}: {r.valor}</li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}
