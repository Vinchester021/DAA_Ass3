package com.example.daa;

import com.example.daa.algorithms.Kruskal;
import com.example.daa.algorithms.MSTResult;
import com.example.daa.algorithms.Prim;
import com.example.daa.graph.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EdgeCaseTest {

    @Test
    void selfLoopDoesNotBreakMst() {

        Graph graph = new Graph(3, false);

        graph.addEdge(0, 0, 1);
        graph.addEdge(0, 1, 5);
        graph.addEdge(1, 2, 7);

        MSTResult kruskal = Kruskal.run(graph);
        MSTResult prim = Prim.run(graph);

        assertEquals(12, kruskal.getTotalWeight());
        assertEquals(12, prim.getTotalWeight());

        assertEquals(2, kruskal.getEdgeCount());
        assertEquals(2, prim.getEdgeCount());
    }

    @Test
    void parallelEdgesUseCheapestEdge() {

        Graph graph = new Graph(3, false);

        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(0, 2, 100);

        MSTResult kruskal = Kruskal.run(graph);
        MSTResult prim = Prim.run(graph);

        assertEquals(5, kruskal.getTotalWeight());
        assertEquals(5, prim.getTotalWeight());
    }

    @Test
    void isolatedVertexCreatesAnotherComponent() {

        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);

        // Vertex 3 is isolated

        MSTResult kruskal = Kruskal.run(graph);
        MSTResult prim = Prim.run(graph);

        assertEquals(3, kruskal.getTotalWeight());
        assertEquals(3, prim.getTotalWeight());

        assertEquals(2, kruskal.getComponents());
        assertEquals(2, prim.getComponents());
    }
}