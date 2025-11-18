import { useState } from "react";
import { useApi } from "../../hooks/useApi.js";
import { API_URL } from "../../api/config.js"; // Asegúrate de importar la URL correcta

export default function BacktrackingComponent() {
    const { request, data, loading, error } = useApi();

    const [origen, setOrigen] = useState("");
    const [destinosRaw, setDestinosRaw] = useState(""); // String para el input (ej: "Morón, Avellaneda")
    const [criterio, setCriterio] = useState("distancia");

    const ejecutar = async () => {
        if (!origen || !destinosRaw) return;

        // Convertimos el string "Morón, Avellaneda" a un array ["Morón", "Avellaneda"]
        const listaDestinos = destinosRaw.split(",").map((d) => d.trim()).filter((d) => d !== "");

        // Construimos el DTO exacto que espera Java (BacktrackingRequestDTO)
        const dto = { 
            origen: origen, 
            destinos: listaDestinos, 
            criterio: criterio 
        };

        await request(`${API_URL}/backtracking`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dto)
        });
    };

    return (
        <div>
            <h2>Backtracking (Ruta Óptima entre Múltiples Puntos)</h2>

            <div className="form-group">
                <label>Origen</label>
                <input 
                    value={origen} 
                    onChange={(e) => setOrigen(e.target.value)} 
                    placeholder="Ej: Quilmes Oeste"
                />
            </div>

            <div className="form-group">
                <label>Destinos a visitar (separados por coma)</label>
                <input 
                    value={destinosRaw} 
                    onChange={(e) => setDestinosRaw(e.target.value)} 
                    placeholder="Ej: Avellaneda, Morón, San Martin"
                />
                <small>El algoritmo buscará la mejor ruta para visitar todos.</small>
            </div>

            <div className="form-group">
                <label>Criterio</label>
                <select value={criterio} onChange={(e) => setCriterio(e.target.value)}>
                    <option value="distancia">Distancia</option>
                    <option value="tiempo">Tiempo</option>
                    <option value="costo">Costo</option>
                </select>
            </div>

            <button className="button-primary" onClick={ejecutar} disabled={loading}>
                {loading ? "Calculando..." : "Ejecutar"}
            </button>

            <div className="result-container">
                <h3>Resultado</h3>

                {error && <p style={{ color: "red" }}>{error}</p>}

                {data && (
                    <>
                        {/* Mapeamos 'ruta' y 'costoTotal' del RutaOptimaDTO */}
                        <div style={{ marginBottom: '15px', padding: '10px', background: '#f0f0f0', borderRadius: '5px' }}>
                            <p><b>Algoritmo:</b> {data.algoritmo}</p>
                            <p><b>Costo Total ({data.criterio}):</b> {data.costoTotal}</p>
                        </div>

                        {data.ruta && data.ruta.length > 0 ? (
                            <p className="ruta-visual">
                                {data.ruta.join(" ➝ ")}
                            </p>
                        ) : (
                            <p>No se encontró una ruta válida que conecte todos los puntos.</p>
                        )}
                    </>
                )}
            </div>
        </div>
    );
}