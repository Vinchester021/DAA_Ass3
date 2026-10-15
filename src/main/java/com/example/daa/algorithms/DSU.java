package com.example.daa.algorithms;

public class DSU {

    private int[] parent;
    private int[] rank;
    private int components;

    private long findCalls;
    private long successfulUnions;

    public DSU(int n) {
        makeSet(n);
    }

    public void makeSet(int n) {
        parent = new int[n];
        rank = new int[n];
        components = n;

        findCalls = 0;
        successfulUnions = 0;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        findCalls++;

        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }

        return parent[x];
    }

    public boolean union(int x, int y) {

        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return false;
        }

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;

        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;

        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }

        components--;
        successfulUnions++;

        return true;
    }

    public int components() {
        return components;
    }

    public long getFindCalls() {
        return findCalls;
    }

    public long getSuccessfulUnions() {
        return successfulUnions;
    }

    public long getDsuOps() {
        return findCalls + successfulUnions;
    }
}