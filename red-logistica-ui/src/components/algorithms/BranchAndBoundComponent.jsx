import { useState } from "react";
import { useApi } from "../../hooks/useApi";

export default function BranchAndBoundComponent() {
    const { request, data, loading, error } = useApi();

    const [origen, setOrigen] = useState("");
    const [destino, setDestino] = useState("");
    const [limite, setLimite] = useState("");

    const ejecutar = async () => {
        const dto = { origen, destino, limite: Number(limite) };
        await request(`/branch-and-bound`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dto)
        });
    };

    return (
        <div>
            <h2>Branch & Bound</h2>

            <div className="form-group">
                <label>Origen</label>
                <input value={origen} onChange={(e) => setOrigen(e.target.value)} />
            </div>

            <div className="form-group">
                <label>Destino</label>
                <input value={destino} onChange={(e) => setDestino(e.target.value)} />
            </div>

            <div className="form-group">
                <label>Límite</label>
                <input value={limite} onChange={(e) => setLimite(e.target.value)} />
            </div>

            <button className="button-primary" onClick={ejecutar} disabled={loading}>
                {loading ? "Explorando..." : "Ejecutar"}
            </button>

            <div className="result-container">
                <h3>Resultado</h3>

                {error && <p style={{ color: "red" }}>{error}</p>}

                {data && (
                    <>
                        <p><b>Camino:</b> {data.camino?.join(" → ")}</p>
                        <p><b>Total:</b> {data.total}</p>
                    </>
                )}
            </div>
        </div>
    );
}
