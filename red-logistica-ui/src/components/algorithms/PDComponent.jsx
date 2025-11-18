import { useState } from "react";
import { useApi } from "../../hooks/useApi";

export default function PDComponent() {
    const { request, data, loading, error } = useApi();
    const [centro, setCentro] = useState("");
    const [capacidad, setCapacidad] = useState("");

    const ejecutar = async () => {
        if (!centro || !capacidad) return;
        await request(`/pd-capacidad?centro=${centro}&capacidad=${capacidad}`, { method: "POST" });
    };

    return (
        <div>
            <h2>Programación Dinámica (Asignación por Capacidad)</h2>

            <div className="form-group">
                <label>Centro</label>
                <input value={centro} onChange={(e) => setCentro(e.target.value)} />
            </div>

            <div className="form-group">
                <label>Capacidad</label>
                <input type="number" value={capacidad} onChange={(e) => setCapacidad(e.target.value)} />
            </div>

            <button className="button-primary" onClick={ejecutar} disabled={loading}>
                {loading ? "Calculando..." : "Ejecutar"}
            </button>

            <div className="result-container">
                <h3>Resultado</h3>

                {error && <p style={{ color: "red" }}>{error}</p>}

                {data && (
                    <>
                        <p><b>Capacidad utilizada:</b> {data.capacidadUsada}</p>
                        <p><b>Clientes asignados:</b></p>
                        <ul>
                            {data.clientes.map((c, i) => (
                                <li key={i}>{c}</li>
                            ))}
                        </ul>
                    </>
                )}
            </div>
        </div>
    );
}
