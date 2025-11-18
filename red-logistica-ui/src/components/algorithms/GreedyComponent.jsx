import { useState } from "react";
import { useApi } from "../../hooks/useApi";

export default function GreedyComponent() {
    const { request, data, loading, error } = useApi();
    const [criterio, setCriterio] = useState("distancia");

    const ejecutar = async () => {
        await request(`/greedy-asignacion?peso=${criterio}`);
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
                                <b>{item.centro} → {item.cliente}</b>  
                                {" — "}Peso: {item.valor}
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}
