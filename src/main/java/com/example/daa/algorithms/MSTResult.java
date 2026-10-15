package com.example.daa.algorithms;

public class MSTResult {

    private final long totalWeight;
    private final int edgeCount;
    private final int components;

    private final long dsuOps;
    private final long edgeScans;
    private final long comparisons;

    public MSTResult(
            long totalWeight,
            int edgeCount,
            int components,
            long dsuOps,
            long edgeScans,
            long comparisons
    ) {
        this.totalWeight = totalWeight;
        this.edgeCount = edgeCount;
        this.components = components;
        this.dsuOps = dsuOps;
        this.edgeScans = edgeScans;
        this.comparisons = comparisons;
    }

    public long getTotalWeight() {
        return totalWeight;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public int getComponents() {
        return components;
    }

    public long getDsuOps() {
        return dsuOps;
    }

    public long getEdgeScans() {
        return edgeScans;
    }

    public long getComparisons() {
        return comparisons;
    }
}