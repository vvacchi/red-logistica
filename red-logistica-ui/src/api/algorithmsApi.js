export async function calcularDijkstra(origen, destino, criterio) {
  const response = await fetch("http://localhost:8080/api/logistica/dijkstra", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ origen, destino, criterio })
  });

  return await response.json();
}
