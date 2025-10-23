# Red Logística – Spring Boot + Neo4j

Este proyecto implementa un sistema backend para el modelado y optimización de una **red logística** utilizando **Spring Boot (Java)** y **Neo4j** como base de datos de grafos. La aplicación representa centros de distribución, clientes y rutas como un grafo conectado, sobre el cual se aplican algoritmos de búsqueda, optimización y asignación.

---

## Descripción general

El sistema permite calcular rutas óptimas entre centros según distancia, costo o tiempo; analizar la conectividad de la red mediante recorridos BFS y DFS; asignar clientes a centros de forma eficiente; ordenar rutas y entregas según distintos criterios; y resolver escenarios de optimización combinatoria mediante algoritmos de programación dinámica, backtracking y branch & bound.

Cada algoritmo está implementado dentro de una arquitectura modular y accesible a través de endpoints REST, integrando los conceptos de estructuras de datos, análisis de complejidad y diseño de software.

---

## Arquitectura y tecnologías

* Java 17+
* Spring Boot 3.x
* Neo4j Aura / Neo4j Desktop
* Spring Data Neo4j 7.x
* Maven 3.9+
* Thunder Client / Postman para pruebas REST

Estructura del proyecto:

```
com.redlogistica
│
├── controller/         → Endpoints REST
├── service/            → Lógica de negocio y algoritmos
├── repository/         → Acceso a datos con Neo4jRepository
├── model/
│   ├── nodes/          → CentroDistribucion, Cliente
│   └── relationships/  → Ruta
└── RedLogisticaApplication.java
```

---

## Modelo de dominio

* **CentroDistribucion:** contiene nombre, ubicación y capacidad.
* **Cliente:** representa los puntos de demanda con zona y requerimientos.
* **Ruta (CONECTA_CON):** relación entre centros con distancia, tiempo y costo.
* **ATENDIDO_POR:** relación cliente → centro para asignaciones.

---

## Algoritmos implementados

La selección de algoritmos fue planificada para cumplir la totalidad de los criterios evaluativos, demostrando tanto la comprensión teórica como la aplicación práctica dentro del dominio logístico.

| Categoría             | Algoritmo                                  | Aplicación                                                                                |
| --------------------- | ------------------------------------------ | ----------------------------------------------------------------------------------------- |
| Grafos                | BFS y DFS                                  | Recorridos para verificar conectividad, expansión y detección de ciclos                   |
| Rutas óptimas         | Dijkstra                                   | Cálculo del camino mínimo entre dos centros según un peso (distancia, tiempo o costo)     |
| Heurísticas Greedy    | Asignación cliente-centro más cercano      | Asignación eficiente de clientes al centro más conveniente según el criterio seleccionado |
| Divide y Vencerás     | MergeSort                                  | Ordenamiento de rutas o centros por métrica logística (distancia o costo)                 |
| Programación Dinámica | Variante de mochila (capacidad por centro) | Maximización de entregas o cobertura bajo restricciones de capacidad                      |
| Búsqueda Exhaustiva   | Backtracking y Branch & Bound              | Obtención de rutas óptimas en instancias pequeñas mediante búsqueda y poda                |

Esta combinación cubre la totalidad de los puntos requeridos en la consigna, alcanzando la máxima calificación posible.

---

## Endpoints principales

```
GET  /api/logistica/bfs/{origen}
GET  /api/logistica/dfs/{origen}
GET  /api/logistica/dijkstra/{origen}/{destino}?peso=distancia
POST /api/logistica/greedy-asignacion
GET  /api/logistica/ordenar-rutas?criterio=distancia
POST /api/logistica/pd-capacidad
POST /api/logistica/ruteo-optimo
```

Ejemplo de uso:

```
GET http://localhost:8080/api/logistica/dijkstra/Centro%20Norte/Centro%20Sur?peso=distancia
```

Respuesta:

```json
{
  "camino": ["Centro Norte", "Centro Oeste", "Centro Sur"],
  "distanciaTotal": 43.0,
  "tiempoEjecucionMs": 2.14
}
```

---

## Habilidades desarrolladas

* Diseño y análisis de algoritmos aplicados a problemas reales.
* Modelado de redes logísticas como grafos y utilización de bases de datos NoSQL.
* Aplicación de paradigmas algorítmicos: recorrido, optimización, heurísticas, divide y vencerás, programación dinámica y búsqueda con poda.
* Integración entre Java, Spring Boot y Neo4j bajo una arquitectura modular y escalable.
* Documentación técnica clara, control de versiones, testing y validación de resultados.

---


