package com.example.daa;

import com.example.daa.algorithms.Kahn;
import com.example.daa.algorithms.TopologicalResult;
import com.example.daa.graph.Edge;
import com.example.daa.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopologicalSortTest {

    @Test
    void validTopologicalOrderForDag() {

        Graph graph = new Graph(6, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 4, 1);
        graph.addEdge(2, 5, 1);

        TopologicalResult result = Kahn.run(graph);

        assertFalse(result.hasCycle());

        assertEquals(
                graph.getVertices(),
                result.getOrder().size()
        );

        assertValidTopologicalOrder(
                graph,
                result.getOrder()
        );
    }

    @Test
    void detectsCycle() {

        Graph graph = new Graph(4, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);
        graph.addEdge(2, 3, 1);

        TopologicalResult result = Kahn.run(graph);

        assertTrue(result.hasCycle());

        assertTrue(
                result.getOrder().size()
                        < graph.getVertices()
        );
    }

    private static void assertValidTopologicalOrder(
            Graph graph,
            List<Integer> order
    ) {

        int[] position =
                new int[graph.getVertices()];

        for (int i = 0; i < order.size(); i++) {
            position[order.get(i)] = i;
        }

        for (Edge edge : graph.getEdges()) {

            assertTrue(
                    position[edge.getFrom()]
                            < position[edge.getTo()]
            );
        }
    }
}