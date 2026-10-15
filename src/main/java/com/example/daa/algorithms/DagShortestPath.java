package com.example.daa.algorithms;

import com.example.daa.graph.Edge;
import com.example.daa.graph.Graph;

import java.util.Arrays;

public class DagShortestPath {

    public static ShortestPathResult run(Graph graph) {

        int n = graph.getVertices();

        TopologicalResult topological = Kahn.run(graph);

        long[] distances = new long[n];
        int[] parents = new int[n];

        Arrays.fill(distances, Long.MAX_VALUE);
        Arrays.fill(parents, -1);

        if (topological.hasCycle()) {
            return new ShortestPathResult(
                    distances,
                    parents,
                    0,
                    true
            );
        }

        int[] inDegree = new int[n];

        for (Edge edge : graph.getEdges()) {
            inDegree[edge.getTo()]++;
        }

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                distances[i] = 0;
            }
        }

        long edgeScans = 0;

        for (int vertex : topological.getOrder()) {

            for (Edge edge : graph.getNeighbors(vertex)) {

                edgeScans++;

                int to = edge.getTo();
                long newDistance =
                        distances[vertex] + edge.getWeight();

                if (newDistance < distances[to]) {
                    distances[to] = newDistance;
                    parents[to] = vertex;
                }
            }
        }

        return new ShortestPathResult(
                distances,
                parents,
                edgeScans,
                false
        );
    }
}