import { useState } from "react";
import { useApi } from "../../hooks/useApi";

export default function DFSComponent() {
    const { request, data, loading, error } = useApi();
    const [origen, setOrigen] = useState("");

    const ejecutar = async () => {
        if (!origen.trim()) return;
        await request(`/dfs/${origen}`);
    };

    return (
        <div>
            <h2>DFS (Recorrido en Profundidad)</h2>

            <div className="form-group">
                <label>Origen</label>
                <input
                    value={origen}
                    onChange={(e) => setOrigen(e.target.value)}
                    placeholder="Ejemplo: Centro Norte"
                />
            </div>

            <button className="button-primary" onClick={ejecutar} disabled={loading}>
                {loading ? "Ejecutando..." : "Ejecutar"}
            </button>

            <div className="result-container">
                <h3>Resultado</h3>

                {error && <p style={{ color: "red" }}>{error}</p>}

                {data && (
                    <p><b>Recorrido:</b> {data.join(" → ")}</p>
                )}
            </div>
        </div>
    );
}
