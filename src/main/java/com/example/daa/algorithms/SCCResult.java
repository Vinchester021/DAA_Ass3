package com.example.daa.algorithms;

public class SCCResult {

    private final int components;
    private final long edgeScans;
    private final int maxDepth;

    public SCCResult(int components, long edgeScans, int maxDepth) {
        this.components = components;
        this.edgeScans = edgeScans;
        this.maxDepth = maxDepth;
    }

    public int getComponents() {
        return components;
    }

    public long getEdgeScans() {
        return edgeScans;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}