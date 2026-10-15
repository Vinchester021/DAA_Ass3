package com.example.daa.algorithms;

import com.example.daa.graph.Edge;
import com.example.daa.graph.Graph;

import java.util.PriorityQueue;

public class Prim {

    public static MSTResult run(Graph graph) {

        int n = graph.getVertices();
        boolean[] inTree = new boolean[n];

        final long[] comparisons = {0};

        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> {
            comparisons[0]++;
            return Integer.compare(a.getWeight(), b.getWeight());
        });

        long totalWeight = 0;
        int edgeCount = 0;
        int components = 0;
        long edgeScans = 0;

        for (int start = 0; start < n; start++) {

            if (inTree[start]) {
                continue;
            }

            components++;
            inTree[start] = true;

            for (Edge edge : graph.getNeighbors(start)) {
                edgeScans++;

                if (!inTree[edge.getTo()]) {
                    pq.add(edge);
                }
            }

            while (!pq.isEmpty()) {

                Edge edge = pq.poll();
                int to = edge.getTo();

                if (inTree[to]) {
                    continue;
                }

                inTree[to] = true;
                totalWeight += edge.getWeight();
                edgeCount++;

                for (Edge next : graph.getNeighbors(to)) {
                    edgeScans++;

                    if (!inTree[next.getTo()]) {
                        pq.add(next);
                    }
                }
            }
        }

        return new MSTResult(
                totalWeight,
                edgeCount,
                components,
                0,
                edgeScans,
                comparisons[0]
        );
    }
}