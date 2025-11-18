import { useState } from "react";
import { useApi } from "../../hooks/useApi";
import { API_URL } from "../../api/config.js"; 

export default function PDComponent() {
    const { request, data, loading, error } = useApi();
    const [centro, setCentro] = useState("");
    const [capacidad, setCapacidad] = useState("");

    const ejecutar = async () => {
        if (!centro || !capacidad) return;
        // Enviamos la petición
        await request(`${API_URL}/pd-capacidad?centro=${centro}&capacidad=${capacidad}`, { method: "POST" });
    };

    return (
        <div>
            <h2>Programación Dinámica (Asignación por Capacidad)</h2>

            <div className="form-group">
                <label>Centro (Nombre exacto)</label>
                <input 
                    value={centro} 
                    onChange={(e) => setCentro(e.target.value)} 
                    placeholder="Ej: Centro Norte"
                />
            </div>

            <div className="form-group">
                <label>Capacidad Máxima</label>
                <input 
                    type="number" 
                    value={capacidad} 
                    onChange={(e) => setCapacidad(e.target.value)} 
                    placeholder="Ej: 200"
                />
            </div>

            <button className="button-primary" onClick={ejecutar} disabled={loading}>
                {loading ? "Calculando..." : "Ejecutar"}
            </button>

            <div className="result-container">
                <h3>Resultado</h3>

                {error && <p style={{ color: "red" }}>{error}</p>}

                {data && (
                    <>
                        <div style={{ marginBottom: '15px', padding: '10px', background: '#f0f0f0', borderRadius: '5px' }}>
                            <p><b>Centro:</b> {data.centro}</p>
                            <p><b>Capacidad Máxima:</b> {data.capacidadMaxima}</p>
                            {/* Usamos toFixed(2) para redondear los decimales largos */}
                            <p><b>Carga Total Lograda:</b> {Number(data.cargaTotal).toFixed(2)}</p>
                            <p><b>Clientes Asignados:</b> {data.cantidadClientes}</p>
                        </div>

                        <h4>Detalle de Clientes Seleccionados:</h4>
                        
                        {data.clientesSeleccionados && data.clientesSeleccionados.length > 0 ? (
                            <ul>
                                {data.clientesSeleccionados.map((cliente, i) => (
                                    <li key={i}>
                                        <b>{cliente.nombre}</b>
                                        {" — "} 
                                        Demanda: {Number(cliente.demanda).toFixed(2)}
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p>No se seleccionaron clientes para esta capacidad.</p>
                        )}
                    </>
                )}
            </div>
        </div>
    );
}