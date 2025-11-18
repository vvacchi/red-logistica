export async function calcularDijkstra(origen, destino, criterio) {
  const url = `http://localhost:8080/api/logistica/dijkstra/${encodeURIComponent(origen)}/${encodeURIComponent(destino)}?peso=${criterio}`;

  const response = await fetch(url, {
    method: "GET"
  });

  return await response.json();
}
