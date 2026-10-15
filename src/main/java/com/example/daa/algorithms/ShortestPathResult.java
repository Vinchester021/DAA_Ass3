package com.example.daa.algorithms;

public class ShortestPathResult {

    private final long[] distances;
    private final int[] parents;
    private final long edgeScans;
    private final boolean hasCycle;

    public ShortestPathResult(
            long[] distances,
            int[] parents,
            long edgeScans,
            boolean hasCycle
    ) {
        this.distances = distances;
        this.parents = parents;
        this.edgeScans = edgeScans;
        this.hasCycle = hasCycle;
    }

    public long[] getDistances() {
        return distances;
    }

    public int[] getParents() {
        return parents;
    }

    public long getEdgeScans() {
        return edgeScans;
    }

    public boolean hasCycle() {
        return hasCycle;
    }
}