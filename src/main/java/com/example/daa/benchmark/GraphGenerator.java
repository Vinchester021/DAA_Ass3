package com.example.daa.benchmark;

import com.example.daa.graph.Graph;

import java.util.Random;

public class GraphGenerator {

    public static Graph generateUndirected(int n, int m) {

        Random rnd = new Random(42);
        Graph graph = new Graph(n, false);

        int[] perm = createPermutation(n, rnd);

        // Random spanning tree
        for (int i = 1; i < n; i++) {

            int a = perm[rnd.nextInt(i)];
            int b = perm[i];
            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        // Remaining random edges
        for (int i = n - 1; i < m; i++) {

            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);

            while (b == a) {
                b = rnd.nextInt(n);
            }

            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        return graph;
    }

    public static Graph generateDirected(int n, int m) {

        Random rnd = new Random(42);
        Graph graph = new Graph(n, true);

        for (int i = 0; i < m; i++) {

            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);

            while (b == a) {
                b = rnd.nextInt(n);
            }

            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        return graph;
    }

    public static Graph generateDAG(int n, int m) {

        Random rnd = new Random(42);
        Graph graph = new Graph(n, true);

        int[] rank = createPermutation(n, rnd);

        for (int i = 0; i < m; i++) {

            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);

            while (b == a) {
                b = rnd.nextInt(n);
            }

            if (rank[a] > rank[b]) {
                int temp = a;
                a = b;
                b = temp;
            }

            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        return graph;
    }

    private static int[] createPermutation(int n, Random rnd) {

        int[] perm = new int[n];

        for (int i = 0; i < n; i++) {
            perm[i] = i;
        }

        for (int i = n - 1; i > 0; i--) {

            int j = rnd.nextInt(i + 1);

            int temp = perm[i];
            perm[i] = perm[j];
            perm[j] = temp;
        }

        return perm;
    }
}