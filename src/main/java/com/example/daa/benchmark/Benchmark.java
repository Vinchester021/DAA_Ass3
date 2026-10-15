package com.example.daa.benchmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import com.example.daa.algorithms.Kosaraju;
import com.example.daa.algorithms.SCCResult;
import com.example.daa.algorithms.Kruskal;
import com.example.daa.algorithms.MSTResult;
import com.example.daa.algorithms.Prim;
import com.example.daa.graph.Graph;
import com.example.daa.algorithms.Kahn;
import com.example.daa.algorithms.TopologicalResult;
import com.example.daa.algorithms.DagShortestPath;
import com.example.daa.algorithms.ShortestPathResult;

import java.util.Arrays;
import java.util.Locale;

public class Benchmark {

    private static final int[] SIZES = {
            1000,
            3162,
            10000,
            31623,
            100000
    };

    private static final int RUNS = 5;

    public static void main(String[] args) throws FileNotFoundException {

        File resultsFolder = new File("results");

        if (!resultsFolder.exists()) {
            resultsFolder.mkdirs();
        }

        try (PrintWriter csv = new PrintWriter("results/results.csv")) {

            csv.println(
                    "algorithm,graph,n,m,time_ms,dsu_ops,edge_scans,comparisons,max_depth,result"
            );

            for (int n : SIZES) {

                int sparseM = 4 * n;
                int denseM = 20 * n;

                runCase(n, sparseM, "sparse", csv);
                runCase(n, denseM, "dense", csv);
            }
        }

        System.out.println("Benchmark finished.");
        System.out.println("results/results.csv created.");
    }

    private static void runCase(
            int n,
            int m,
            String density,
            PrintWriter csv
    ) {

        System.out.println(
                "Running n=" + n +
                        ", m=" + m +
                        ", " + density
        );

        Graph undirected =
                GraphGenerator.generateUndirected(n, m);

        double[] kruskalTimes = new double[RUNS];
        double[] primTimes = new double[RUNS];

        MSTResult kruskalResult = null;
        MSTResult primResult = null;

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();
            MSTResult result = Kruskal.run(undirected);
            long end = System.nanoTime();

            kruskalTimes[run] =
                    (end - start) / 1_000_000.0;

            kruskalResult = result;
        }

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();
            MSTResult result = Prim.run(undirected);
            long end = System.nanoTime();

            primTimes[run] =
                    (end - start) / 1_000_000.0;

            primResult = result;
        }

        if (kruskalResult.getTotalWeight()
                != primResult.getTotalWeight()) {

            throw new IllegalStateException(
                    "Kruskal and Prim weights are different"
            );
        }

        csv.printf(
                Locale.US,
                "Kruskal,undirected-%s,%d,%d,%.4f,%d,%d,%d,%d,%d%n",
                density,
                n,
                m,
                median(kruskalTimes),
                kruskalResult.getDsuOps(),
                kruskalResult.getEdgeScans(),
                kruskalResult.getComparisons(),
                0,
                kruskalResult.getTotalWeight()
        );

        csv.printf(
                Locale.US,
                "Prim,undirected-%s,%d,%d,%.4f,%d,%d,%d,%d,%d%n",
                density,
                n,
                m,
                median(primTimes),
                primResult.getDsuOps(),
                primResult.getEdgeScans(),
                primResult.getComparisons(),
                0,
                primResult.getTotalWeight()
        );

        Graph directed =
                GraphGenerator.generateDirected(n, m);

        double[] kosarajuTimes = new double[RUNS];

        SCCResult kosarajuResult = null;

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();
            SCCResult result = Kosaraju.run(directed);
            long end = System.nanoTime();

            kosarajuTimes[run] =
                    (end - start) / 1_000_000.0;

            kosarajuResult = result;
        }

        csv.printf(
                Locale.US,
                "Kosaraju,directed-%s,%d,%d,%.4f,%d,%d,%d,%d,%d%n",
                density,
                n,
                m,
                median(kosarajuTimes),
                0,
                kosarajuResult.getEdgeScans(),
                0,
                kosarajuResult.getMaxDepth(),
                kosarajuResult.getComponents()
        );


        Graph dag =
                GraphGenerator.generateDAG(n, m);


// KAHN

        double[] kahnTimes = new double[RUNS];

        TopologicalResult kahnResult = null;

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();
            TopologicalResult result = Kahn.run(dag);
            long end = System.nanoTime();

            kahnTimes[run] =
                    (end - start) / 1_000_000.0;

            kahnResult = result;
        }

        if (kahnResult.hasCycle()
                || kahnResult.getOrder().size() != n) {

            throw new IllegalStateException(
                    "Kahn returned an invalid result"
            );
        }

        csv.printf(
                Locale.US,
                "Kahn,dag-%s,%d,%d,%.4f,%d,%d,%d,%d,%d%n",
                density,
                n,
                m,
                median(kahnTimes),
                0,
                kahnResult.getEdgeScans(),
                0,
                0,
                kahnResult.getOrder().size()
        );


// DAG SHORTEST PATH

        double[] shortestTimes = new double[RUNS];

        ShortestPathResult shortestResult = null;

        for (int run = 0; run < RUNS; run++) {

            long start = System.nanoTime();
            ShortestPathResult result =
                    DagShortestPath.run(dag);
            long end = System.nanoTime();

            shortestTimes[run] =
                    (end - start) / 1_000_000.0;

            shortestResult = result;
        }

        if (shortestResult.hasCycle()) {
            throw new IllegalStateException(
                    "DAG shortest path detected a cycle"
            );
        }

        long distanceSum = 0;

        for (long distance : shortestResult.getDistances()) {

            if (distance == Long.MAX_VALUE) {
                throw new IllegalStateException(
                        "Unreachable vertex in generated DAG"
                );
            }

            distanceSum += distance;
        }

        csv.printf(
                Locale.US,
                "DagShortestPath,dag-%s,%d,%d,%.4f,%d,%d,%d,%d,%d%n",
                density,
                n,
                m,
                median(shortestTimes),
                0,
                shortestResult.getEdgeScans(),
                0,
                0,
                distanceSum
        );

        checkReferenceValues(
                n,
                m,
                kruskalResult.getTotalWeight(),
                kosarajuResult.getComponents(),
                distanceSum
        );


        checkEdgeScans(
                m,
                kosarajuResult,
                kahnResult,
                shortestResult
        );

    }

    private static double median(double[] values) {

        double[] copy = values.clone();

        Arrays.sort(copy);

        return copy[copy.length / 2];
    }

    private static void checkReferenceValues(
            int n,
            int m,
            long mstWeight,
            int sccCount,
            long dagDistanceSum
    ) {

        long expectedMst;
        int expectedScc;
        long expectedDagSum;

        String key = n + "-" + m;

        switch (key) {

            case "1000-4000" -> {
                expectedMst = 147970;
                expectedScc = 27;
                expectedDagSum = 396274;
            }

            case "1000-20000" -> {
                expectedMst = 30363;
                expectedScc = 1;
                expectedDagSum = 252411;
            }

            case "3162-12648" -> {
                expectedMst = 468911;
                expectedScc = 137;
                expectedDagSum = 1271911;
            }

            case "3162-63240" -> {
                expectedMst = 97589;
                expectedScc = 1;
                expectedDagSum = 723157;
            }

            case "10000-40000" -> {
                expectedMst = 1486139;
                expectedScc = 373;
                expectedDagSum = 3941828;
            }

            case "10000-200000" -> {
                expectedMst = 309550;
                expectedScc = 1;
                expectedDagSum = 2381342;
            }

            case "31623-126492" -> {
                expectedMst = 4715515;
                expectedScc = 1188;
                expectedDagSum = 12551891;
            }

            case "31623-632460" -> {
                expectedMst = 968517;
                expectedScc = 1;
                expectedDagSum = 7376612;
            }

            case "100000-400000" -> {
                expectedMst = 15012094;
                expectedScc = 3923;
                expectedDagSum = 40096539;
            }

            case "100000-2000000" -> {
                expectedMst = 3056681;
                expectedScc = 1;
                expectedDagSum = 23512087;
            }

            default ->
                    throw new IllegalArgumentException(
                            "No reference values for n="
                                    + n + ", m=" + m
                    );
        }

        if (mstWeight != expectedMst) {
            throw new IllegalStateException(
                    "Wrong MST weight for n=" + n +
                            ", m=" + m +
                            ". Expected " + expectedMst +
                            ", got " + mstWeight
            );
        }

        if (sccCount != expectedScc) {
            throw new IllegalStateException(
                    "Wrong SCC count for n=" + n +
                            ", m=" + m +
                            ". Expected " + expectedScc +
                            ", got " + sccCount
            );
        }

        if (dagDistanceSum != expectedDagSum) {
            throw new IllegalStateException(
                    "Wrong DAG distance sum for n=" + n +
                            ", m=" + m +
                            ". Expected " + expectedDagSum +
                            ", got " + dagDistanceSum
            );
        }
    }


    private static void checkEdgeScans(
            int m,
            SCCResult kosarajuResult,
            TopologicalResult kahnResult,
            ShortestPathResult shortestResult
    ) {

        if (kosarajuResult.getEdgeScans() != 2L * m) {
            throw new IllegalStateException(
                    "Wrong Kosaraju edge scans. Expected "
                            + (2L * m)
                            + ", got "
                            + kosarajuResult.getEdgeScans()
            );
        }

        if (kahnResult.getEdgeScans() != m) {
            throw new IllegalStateException(
                    "Wrong Kahn edge scans. Expected "
                            + m
                            + ", got "
                            + kahnResult.getEdgeScans()
            );
        }

        if (shortestResult.getEdgeScans() != m) {
            throw new IllegalStateException(
                    "Wrong DAG shortest path edge scans. Expected "
                            + m
                            + ", got "
                            + shortestResult.getEdgeScans()
            );
        }
    }
}