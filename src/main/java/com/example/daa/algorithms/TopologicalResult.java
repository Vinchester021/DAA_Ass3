package com.example.daa.algorithms;

import java.util.List;

public class TopologicalResult {

    private final List<Integer> order;
    private final boolean hasCycle;
    private final long edgeScans;

    public TopologicalResult(
            List<Integer> order,
            boolean hasCycle,
            long edgeScans
    ) {
        this.order = order;
        this.hasCycle = hasCycle;
        this.edgeScans = edgeScans;
    }

    public List<Integer> getOrder() {
        return order;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public long getEdgeScans() {
        return edgeScans;
    }
}