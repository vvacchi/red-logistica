import React, { useState } from 'react';
import { useApi } from '../hooks/useApi';
// Importamos los iconos que vamos a usar
import { FaMapMarkerAlt, FaRoute, FaWeightHanging, FaTruck } from 'react-icons/fa';

function DijkstraComponent() {
    const [origen, setOrigen] = useState('Centro Sur');
    const [destino, setDestino] = useState('Centro Sudeste');
    const [peso, setPeso] = useState('distancia');

    // Usamos nuestro custom hook para la lógica de la API
    const { data: resultado, error, loading, request } = useApi();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const url = `http://localhost:8080/api/logistica/dijkstra/${encodeURIComponent(origen)}/${encodeURIComponent(destino)}?peso=${peso}`;
        
        // Simplemente llamamos a la función request de nuestro hook
        await request(url);
    };

    return (
        <div className="card">
            <h2>Calcular Ruta Óptima (Dijkstra)</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-grid">
                    <div className="form-group">
                        <label><FaMapMarkerAlt /> Origen:</label>
                        <input type="text" value={origen} onChange={(e) => setOrigen(e.target.value)} required />
                    </div>
                    <div className="form-group">
                        <label><FaMapMarkerAlt /> Destino:</label>
                        <input type="text" value={destino} onChange={(e) => setDestino(e.target.value)} required />
                    </div>
                    <div className="form-group">
                        <label><FaWeightHanging /> Criterio (peso):</label>
                        <select value={peso} onChange={(e) => setPeso(e.target.value)}>
                            <option value="distancia">Distancia</option>
                            <option value="tiempo">Tiempo</option>
                            <option value="costo">Costo</option>
                        </select>
                    </div>
                </div>
                <button type="submit" disabled={loading} className="button-primary">
                    {loading ? 'Calculando...' : <><FaRoute /> Calcular Ruta</>}
                </button>
            </form>

            {error && <p style={{ color: 'red' }}>Error: {error}</p>}

            {resultado && (
                <div className="result-container">
                    <h3>Resultado</h3>
                    <p><strong><FaTruck /> Camino:</strong> {resultado.camino.join(' → ')}</p>
                    <p><strong>{resultado.criterio || peso} Total:</strong> {resultado.distanciaTotal.toFixed(2)}</p>
                    <p><strong>Tiempo de ejecución (ms):</strong> {resultado.tiempoEjecucionMs}</p>
                </div>
            )}
        </div>
    );
}

export default DijkstraComponent;
