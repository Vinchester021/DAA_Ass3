package com.example.daa.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphReader {

    public static Graph readFromFile(String fileName) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String[] firstLine = reader.readLine().trim().split("\\s+");

            int n = Integer.parseInt(firstLine[0]);
            int m = Integer.parseInt(firstLine[1]);
            boolean directed = firstLine[2].equalsIgnoreCase("directed");

            Graph graph = new Graph(n, directed);

            for (int i = 0; i < m; i++) {

                String[] parts = reader.readLine().trim().split("\\s+");

                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                int weight = Integer.parseInt(parts[2]);

                graph.addEdge(from, to, weight);
            }

            return graph;
        }
    }
}