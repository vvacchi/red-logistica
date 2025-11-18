import { createContext, useContext, useState } from "react";

const AlgorithmContext = createContext();

export function AlgorithmProvider({ children }) {
  const [algorithm, setAlgorithm] = useState("dijkstra");

  return (
    <AlgorithmContext.Provider value={{ algorithm, setAlgorithm }}>
      {children}
    </AlgorithmContext.Provider>
  );
}

export function useAlgorithm() {
  return useContext(AlgorithmContext);
}
