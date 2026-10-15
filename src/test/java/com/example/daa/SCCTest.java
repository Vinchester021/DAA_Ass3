package com.example.daa;

import com.example.daa.algorithms.Kosaraju;
import com.example.daa.algorithms.SCCResult;
import com.example.daa.graph.Edge;
import com.example.daa.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SCCTest {

    @Test
    void kosarajuMatchesBruteForce() {

        Random rnd = new Random(42);

        for (int test = 0; test < 50; test++) {

            int n = 2 + rnd.nextInt(199);

            Graph graph = new Graph(n, true);

            int m = n * 3;

            for (int i = 0; i < m; i++) {

                int a = rnd.nextInt(n);
                int b = rnd.nextInt(n);

                graph.addEdge(a, b, 1);
            }

            SCCResult result = Kosaraju.run(graph);

            int expected =
                    bruteForceSccCount(graph);

            assertEquals(
                    expected,
                    result.getComponents()
            );
        }
    }

    private static int bruteForceSccCount(Graph graph) {

        int n = graph.getVertices();

        Graph reversed = new Graph(n, true);

        for (Edge edge : graph.getEdges()) {
            reversed.addEdge(
                    edge.getTo(),
                    edge.getFrom(),
                    edge.getWeight()
            );
        }

        boolean[] assigned = new boolean[n];

        int components = 0;

        for (int vertex = 0; vertex < n; vertex++) {

            if (assigned[vertex]) {
                continue;
            }

            boolean[] forward =
                    bfs(graph, vertex);

            boolean[] backward =
                    bfs(reversed, vertex);

            for (int i = 0; i < n; i++) {

                if (forward[i] && backward[i]) {
                    assigned[i] = true;
                }
            }

            components++;
        }

        return components;
    }

    private static boolean[] bfs(
            Graph graph,
            int start
    ) {

        boolean[] visited =
                new boolean[graph.getVertices()];

        ArrayDeque<Integer> queue =
                new ArrayDeque<>();

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {

            int vertex = queue.remove();

            for (Edge edge :
                    graph.getNeighbors(vertex)) {

                int to = edge.getTo();

                if (!visited[to]) {
                    visited[to] = true;
                    queue.add(to);
                }
            }
        }

        return visited;
    }
}