import React from "react";
import { useAlgorithm } from "../context/AlgorithmContext.jsx";

// Importar componentes
import DijkstraComponent from "../components/algorithms/DijkstraComponent.jsx";
import BFSComponent from "../components/algorithms/BFSComponent.jsx";
import DFSComponent from "../components/algorithms/DFSComponent.jsx";
import GreedyComponent from "../components/algorithms/GreedyComponent.jsx";
import MergeSortComponent from "../components/algorithms/MergeSortComponent.jsx";
import PDComponent from "../components/algorithms/PDComponent.jsx";
import BacktrackingComponent from "../components/algorithms/BacktrackingComponent.jsx";
import BranchAndBoundComponent from "../components/algorithms/BranchAndBoundComponent.jsx";

export default function AlgorithmRunner() {
  const { algorithm } = useAlgorithm();

  const render = () => {
    switch (algorithm) {
      case "dijkstra":
        return <DijkstraComponent />;

      case "bfs":
        return <BFSComponent />;

      case "dfs":
        return <DFSComponent />;

      case "greedy":
        return <GreedyComponent />;

      case "mergesort":
        return <MergeSortComponent />;

      case "pd":
        return <PDComponent />;

      case "backtracking":
        return <BacktrackingComponent />;

      case "branchbound":
        return <BranchAndBoundComponent />;

      default:
        return null;
    }
  };

  return <main>{render()}</main>;
}
