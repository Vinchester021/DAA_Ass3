package com.example.daa;

import com.example.daa.algorithms.Kruskal;
import com.example.daa.algorithms.MSTResult;
import com.example.daa.algorithms.Prim;
import com.example.daa.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MSTTest {

    @Test
    void kruskalAndPrimMatchOnRandomGraphs() {

        Random rnd = new Random(42);

        for (int test = 0; test < 100; test++) {

            int n = 10 + rnd.nextInt(40);

            Graph graph = new Graph(n, false);

            // First create a spanning tree
            // so the graph is guaranteed to be connected
            for (int i = 1; i < n; i++) {

                int parent = rnd.nextInt(i);
                int weight = 1 + rnd.nextInt(1000);

                graph.addEdge(parent, i, weight);
            }

            // Add extra random edges
            int extraEdges = n * 2;

            for (int i = 0; i < extraEdges; i++) {

                int a = rnd.nextInt(n);
                int b = rnd.nextInt(n);

                while (a == b) {
                    b = rnd.nextInt(n);
                }

                int weight = 1 + rnd.nextInt(1000);

                graph.addEdge(a, b, weight);
            }

            MSTResult kruskal = Kruskal.run(graph);
            MSTResult prim = Prim.run(graph);

            assertEquals(
                    kruskal.getTotalWeight(),
                    prim.getTotalWeight()
            );

            assertEquals(
                    n - 1,
                    kruskal.getEdgeCount()
            );

            assertEquals(
                    n - 1,
                    prim.getEdgeCount()
            );

            assertEquals(
                    1,
                    kruskal.getComponents()
            );

            assertEquals(
                    1,
                    prim.getComponents()
            );
        }
    }
}