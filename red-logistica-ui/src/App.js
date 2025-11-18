import React from "react";
import "./App.css";

import Sidebar from "./components/layout/Sidebar.jsx";
import AlgorithmRunner from "./pages/AlgorithmRunner.jsx";
import { AlgorithmProvider } from "./context/AlgorithmContext.jsx";


export default function App() {
  return (
    <AlgorithmProvider>
      <header className="App-header">
        Red Logística – Panel de Algoritmos
      </header>

      <div className="layout">
        <Sidebar />
        <AlgorithmRunner />
      </div>
    </AlgorithmProvider>
  );
}
