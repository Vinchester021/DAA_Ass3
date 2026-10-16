package com.example.daa;

import com.example.daa.algorithms.*;
import com.example.daa.graph.Graph;
import com.example.daa.graph.GraphReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputGraphTest {

    @Test
    void readsNormalGraph() throws Exception {

        Graph graph =
                GraphReader.readFromFile("data/graph1.txt");

        assertEquals(5, graph.getVertices());
        assertEquals(6, graph.getEdges().size());
        assertFalse(graph.isDirected());
    }

    @Test
    void disconnectedGraphReturnsForest() throws Exception {

        Graph graph =
                GraphReader.readFromFile("data/disconnected.txt");

        MSTResult kruskal = Kruskal.run(graph);
        MSTResult prim = Prim.run(graph);

        assertEquals(15, kruskal.getTotalWeight());
        assertEquals(15, prim.getTotalWeight());

        assertEquals(2, kruskal.getComponents());
        assertEquals(2, prim.getComponents());
    }

    @Test
    void cyclicGraphDetectsCycleAndScc() throws Exception {

        Graph graph =
                GraphReader.readFromFile("data/cyclic.txt");

        TopologicalResult topological =
                Kahn.run(graph);

        SCCResult scc =
                Kosaraju.run(graph);

        assertTrue(topological.hasCycle());
        assertEquals(3, scc.getComponents());
    }
}