import { API_URL } from "./config";

// --- Dijkstra ---
export async function calcularDijkstra(origen, destino, criterio) {
  const res = await fetch(
    `${API_URL}/dijkstra/${encodeURIComponent(origen)}/${encodeURIComponent(
      destino
    )}?peso=${encodeURIComponent(criterio)}`
  );
  return res.json();
}

// --- BFS ---
export async function ejecutarBFS(origen) {
  const res = await fetch(
    `${API_URL}/bfs/${encodeURIComponent(origen)}`
  );
  return res.json();
}

// --- DFS ---
export async function ejecutarDFS(origen) {
  const res = await fetch(
    `${API_URL}/dfs/${encodeURIComponent(origen)}`
  );
  return res.json();
}

// --- Greedy ---
export async function ejecutarGreedy(criterio) {
  const res = await fetch(
    `${API_URL}/greedy-asignacion?peso=${encodeURIComponent(criterio)}`
  );
  return res.json();
}

// --- MergeSort ---
export async function ejecutarMergeSort(criterio) {
  const res = await fetch(
    `${API_URL}/ordenar-rutas?criterio=${encodeURIComponent(criterio)}`
  );
  return res.json();
}

// --- Programación Dinámica ---
export async function ejecutarPD(centro, capacidad) {
  const res = await fetch(
    `${API_URL}/pd-capacidad?centro=${encodeURIComponent(
      centro
    )}&capacidad=${encodeURIComponent(capacidad)}`,
    { method: "POST" }
  );
  return res.json();
}

// --- Backtracking ---
export async function ejecutarBacktracking(data) {
  const res = await fetch(`${API_URL}/backtracking`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  return res.json();
}

// --- Branch & Bound ---
export async function ejecutarBranchAndBound(data) {
  const res = await fetch(`${API_URL}/branch-and-bound`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  return res.json();
}
