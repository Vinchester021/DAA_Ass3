package com.example.daa;

import com.example.daa.algorithms.DagShortestPath;
import com.example.daa.algorithms.ShortestPathResult;
import com.example.daa.graph.Edge;
import com.example.daa.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DagShortestPathTest {

    @Test
    void matchesBellmanFordOnRandomDags() {

        Random rnd = new Random(42);

        for (int test = 0; test < 100; test++) {

            int n = 5 + rnd.nextInt(26);

            Graph graph = new Graph(n, true);

            int attempts = n * 3;

            for (int i = 0; i < attempts; i++) {

                int a = rnd.nextInt(n);
                int b = rnd.nextInt(n);

                if (a == b) {
                    continue;
                }

                int from = Math.min(a, b);
                int to = Math.max(a, b);

                int weight = rnd.nextInt(41) - 20;

                graph.addEdge(from, to, weight);
            }

            ShortestPathResult result =
                    DagShortestPath.run(graph);

            long[] expected =
                    bellmanFord(graph);

            assertFalse(result.hasCycle());

            assertArrayEquals(
                    expected,
                    result.getDistances()
            );
        }
    }

    @Test
    void handlesNegativeWeights() {

        Graph graph = new Graph(5, true);

        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, -3);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 4, 2);

        ShortestPathResult result =
                DagShortestPath.run(graph);

        long[] expected = {
                0,
                2,
                -1,
                7,
                1
        };

        assertFalse(result.hasCycle());

        assertArrayEquals(
                expected,
                result.getDistances()
        );
    }

    private static long[] bellmanFord(Graph graph) {

        int n = graph.getVertices();

        long[] distance = new long[n];

        Arrays.fill(
                distance,
                Long.MAX_VALUE
        );

        int[] inDegree = new int[n];

        for (Edge edge : graph.getEdges()) {
            inDegree[edge.getTo()]++;
        }

        for (int i = 0; i < n; i++) {

            if (inDegree[i] == 0) {
                distance[i] = 0;
            }
        }

        for (int i = 0; i < n - 1; i++) {

            boolean changed = false;

            for (Edge edge : graph.getEdges()) {

                int from = edge.getFrom();
                int to = edge.getTo();

                if (distance[from] == Long.MAX_VALUE) {
                    continue;
                }

                long newDistance =
                        distance[from]
                                + edge.getWeight();

                if (newDistance < distance[to]) {

                    distance[to] = newDistance;
                    changed = true;
                }
            }

            if (!changed) {
                break;
            }
        }

        return distance;
    }
}