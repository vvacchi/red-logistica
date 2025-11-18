import React from "react";
import { useAlgorithm } from "../context/AlgorithmContext.jsx";

// Importar componentes
import DijkstraComponent from "../components/algorithms/DijkstraComponent.jsx";
import BFSComponent from "../components/algorithms/BFSComponent";

export default function AlgorithmRunner() {
  const { algorithm } = useAlgorithm();

  const render = () => {
    switch (algorithm) {
      case "dijkstra":
        return <DijkstraComponent />;

      case "bfs":
        return <BFSComponent />;

      case "dfs":
        return <div className="card"><h2>DFS</h2><p>Próximamente</p></div>;

      case "greedy":
        return <div className="card"><h2>Greedy</h2><p>Próximamente</p></div>;

      case "mergesort":
        return <div className="card"><h2>MergeSort</h2><p>Próximamente</p></div>;

      case "pd":
        return <div className="card"><h2>Programación Dinámica</h2><p>Próximamente</p></div>;

      case "backtracking":
        return <div className="card"><h2>Backtracking</h2><p>Próximamente</p></div>;

      case "branchbound":
        return <div className="card"><h2>Branch & Bound</h2><p>Próximamente</p></div>;

      default:
        return null;
    }
  };

  return <main>{render()}</main>;
}
