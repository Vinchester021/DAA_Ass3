package com.example.daa.algorithms;

import com.example.daa.graph.Edge;
import com.example.daa.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Kahn {

    public static TopologicalResult run(Graph graph) {

        int n = graph.getVertices();

        int[] inDegree = new int[n];

        // Calculate in-degrees
        for (Edge edge : graph.getEdges()) {
            inDegree[edge.getTo()]++;
        }

        ArrayDeque<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();
        long edgeScans = 0;

        while (!queue.isEmpty()) {

            int vertex = queue.remove();
            order.add(vertex);

            for (Edge edge : graph.getNeighbors(vertex)) {

                edgeScans++;

                int to = edge.getTo();
                inDegree[to]--;

                if (inDegree[to] == 0) {
                    queue.add(to);
                }
            }
        }

        boolean hasCycle = order.size() < n;

        return new TopologicalResult(
                order,
                hasCycle,
                edgeScans
        );
    }
}