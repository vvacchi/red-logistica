import React from "react";
import { useAlgorithm } from "../../context/AlgorithmContext.jsx";

export default function Sidebar() {
  const { algorithm, setAlgorithm } = useAlgorithm();

  const algorithms = [
    { id: "dijkstra", name: "Dijkstra" },
    { id: "bfs", name: "BFS" },
    { id: "dfs", name: "DFS" },
    { id: "greedy", name: "Greedy" },
    { id: "mergesort", name: "MergeSort" },
    { id: "pd", name: "Programación Dinámica" },
    { id: "backtracking", name: "Backtracking" },
    { id: "branchbound", name: "Branch & Bound" }
  ];

  return (
    <aside className="sidebar">
      <h3>Algoritmos</h3>
      <ul>
        {algorithms.map(a => (
          <li
            key={a.id}
            className={algorithm === a.id ? "active" : ""}
            onClick={() => setAlgorithm(a.id)}
          >
            {a.name}
          </li>
        ))}
      </ul>
    </aside>
  );
}
