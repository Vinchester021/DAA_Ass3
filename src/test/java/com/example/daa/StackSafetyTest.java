package com.example.daa;

import com.example.daa.algorithms.Kosaraju;
import com.example.daa.algorithms.SCCResult;
import com.example.daa.graph.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StackSafetyTest {

    @Test
    void kosarajuHandlesLargePathWithoutStackOverflow() {

        int n = 100000;

        Graph graph = new Graph(n, true);

        for (int i = 0; i < n - 1; i++) {
            graph.addEdge(i, i + 1, 1);
        }

        SCCResult result =
                assertDoesNotThrow(
                        () -> Kosaraju.run(graph)
                );

        assertEquals(
                n,
                result.getComponents()
        );

        assertEquals(
                2L * (n - 1),
                result.getEdgeScans()
        );
    }
}