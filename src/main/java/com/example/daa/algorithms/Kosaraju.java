package com.example.daa.algorithms;

import com.example.daa.graph.Edge;
import com.example.daa.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Kosaraju {

    public static SCCResult run(Graph graph) {

        int n = graph.getVertices();

        boolean[] visited = new boolean[n];
        int[] nextIndex = new int[n];

        List<Integer> finishOrder = new ArrayList<>();

        long edgeScans = 0;
        int maxDepth = 0;

        // First DFS pass
        for (int start = 0; start < n; start++) {

            if (visited[start]) {
                continue;
            }

            ArrayDeque<Integer> stack = new ArrayDeque<>();

            visited[start] = true;
            stack.push(start);

            maxDepth = Math.max(maxDepth, stack.size());

            while (!stack.isEmpty()) {

                int vertex = stack.peek();
                List<Edge> neighbors = graph.getNeighbors(vertex);

                if (nextIndex[vertex] < neighbors.size()) {

                    Edge edge = neighbors.get(nextIndex[vertex]);
                    nextIndex[vertex]++;

                    edgeScans++;

                    int to = edge.getTo();

                    if (!visited[to]) {
                        visited[to] = true;
                        stack.push(to);

                        maxDepth = Math.max(maxDepth, stack.size());
                    }

                } else {
                    stack.pop();
                    finishOrder.add(vertex);
                }
            }
        }

        // Reverse graph
        Graph reversed = new Graph(n, true);

        for (Edge edge : graph.getEdges()) {
            reversed.addEdge(
                    edge.getTo(),
                    edge.getFrom(),
                    edge.getWeight()
            );
        }

        // Second DFS pass
        visited = new boolean[n];

        int components = 0;

        for (int i = finishOrder.size() - 1; i >= 0; i--) {

            int start = finishOrder.get(i);

            if (visited[start]) {
                continue;
            }

            components++;

            ArrayDeque<Integer> stack = new ArrayDeque<>();

            visited[start] = true;
            stack.push(start);

            maxDepth = Math.max(maxDepth, stack.size());

            while (!stack.isEmpty()) {

                int vertex = stack.pop();

                for (Edge edge : reversed.getNeighbors(vertex)) {

                    edgeScans++;

                    int to = edge.getTo();

                    if (!visited[to]) {
                        visited[to] = true;
                        stack.push(to);

                        maxDepth = Math.max(maxDepth, stack.size());
                    }
                }
            }
        }

        return new SCCResult(
                components,
                edgeScans,
                maxDepth
        );
    }
}