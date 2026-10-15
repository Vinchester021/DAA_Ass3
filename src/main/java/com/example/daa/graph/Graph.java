package com.example.daa.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final int vertices;
    private final boolean directed;
    private final List<List<Edge>> adjacencyList;
    private final List<Edge> edges;

    public Graph(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;

        adjacencyList = new ArrayList<>();
        edges = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int weight) {
        Edge edge = new Edge(from, to, weight);

        adjacencyList.get(from).add(edge);
        edges.add(edge);

        if (!directed) {
            Edge reverseEdge = new Edge(to, from, weight);
            adjacencyList.get(to).add(reverseEdge);
        }
    }

    public int getVertices() {
        return vertices;
    }

    public boolean isDirected() {
        return directed;
    }

    public List<Edge> getNeighbors(int vertex) {
        return adjacencyList.get(vertex);
    }

    public List<Edge> getEdges() {
        return edges;
    }
}