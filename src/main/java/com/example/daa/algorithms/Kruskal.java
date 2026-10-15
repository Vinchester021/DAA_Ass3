package com.example.daa.algorithms;

import com.example.daa.graph.Edge;
import com.example.daa.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class Kruskal {

    public static MSTResult run(Graph graph) {

        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());

        final long[] comparisons = {0};

        sortedEdges.sort((a, b) -> {
            comparisons[0]++;
            return Integer.compare(a.getWeight(), b.getWeight());
        });

        DSU dsu = new DSU(graph.getVertices());

        long totalWeight = 0;
        int edgeCount = 0;
        long edgeScans = 0;

        for (Edge edge : sortedEdges) {

            edgeScans++;

            int from = edge.getFrom();
            int to = edge.getTo();

            if (dsu.find(from) != dsu.find(to)) {

                dsu.union(from, to);

                totalWeight += edge.getWeight();
                edgeCount++;

                if (dsu.components() == 1) {
                    break;
                }
            }
        }

        return new MSTResult(
                totalWeight,
                edgeCount,
                dsu.components(),
                dsu.getDsuOps(),
                edgeScans,
                comparisons[0]
        );
    }
}