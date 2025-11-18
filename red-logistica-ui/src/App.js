// src/App.js
import React from 'react';
import DijkstraComponent from '../src/components/DijkstraComponent.jsx';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Dashboard de Red Logística</h1>
      </header>
      <main>
        <DijkstraComponent />
        {/* Aquí puedes agregar más componentes para las otras funcionalidades */}
      </main>
    </div>
  );
}

export default App;
