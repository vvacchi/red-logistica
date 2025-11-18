import { useState } from "react";
import { useApi } from "../../hooks/useApi.js";
import { API_URL } from "../../api/config.js"; 

export default function BranchAndBoundComponent() {
    const { request, data, loading, error } = useApi();

    // 1. EL USUARIO INGRESA TODOS LOS NODOS QUE DEBE VISITAR
    const [nodosRaw, setNodosRaw] = useState(""); 
    const [criterio, setCriterio] = useState("distancia");

    const ejecutar = async () => {
        if (!nodosRaw) return;

        // Convertimos el string "A, B, C" a un array ["A", "B", "C"]
        const listaNodos = nodosRaw.split(",").map((n) => n.trim()).filter((n) => n !== "");

        if (listaNodos.length < 2) {
            alert("Necesitas al menos dos nodos para calcular una ruta óptima.");
            return;
        }
        
        // Construimos el DTO exacto que espera Java (BranchAndBoundRequestDTO)
        const dto = { 
            nodos: listaNodos, 
            criterio: criterio 
        };

        // El endpoint es el mismo que para Backtracking, pero la lógica del service es diferente.
        await request(`${API_URL}/branch-and-bound`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dto)
        });
    };

    return (
        <div>
            <h2>Branch & Bound (Ruta Óptima - TSP)</h2>
            <small>Calcula el camino más corto que visita todos los nodos listados, utilizando cotas para acelerar la búsqueda.</small>
            
            <div className="form-group" style={{ marginTop: '15px' }}>
                <label>Nodos a Visitar (separados por coma)</label>
                <input 
                    value={nodosRaw} 
                    onChange={(e) => setNodosRaw(e.target.value)} 
                    placeholder="Ej: Quilmes Oeste, Avellaneda, Morón, Ituzaingo"
                />
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
                {loading ? "Calculando cota..." : "Ejecutar"}
            </button>

            <div className="result-container">
                <h3>Resultado</h3>

                {error && <p style={{ color: "red" }}>{error}</p>}

                {data && (
                    <>
                        {/* Mapeamos la respuesta del RutaOptimaDTO */}
                        <div style={{ marginBottom: '15px', padding: '10px', background: '#f0f0f0', borderRadius: '5px' }}>
                            <p><b>Algoritmo:</b> {data.algoritmo}</p>
                            <p><b>Costo Total ({data.criterio}):</b> {Number(data.costoTotal).toFixed(2)}</p>
                            <p><b>Número de Nodos Optimizados:</b> {data.ruta ? data.ruta.length : 0}</p>
                        </div>

                        {data.ruta && data.ruta.length > 0 ? (
                            <p className="ruta-visual" style={{ fontWeight: 'bold' }}>
                                {data.ruta.join(" ➝ ")}
                            </p>
                        ) : (
                            <p>No se encontró una ruta válida para visitar todos los nodos.</p>
                        )}
                    </>
                )}
            </div>
        </div>
    );
}