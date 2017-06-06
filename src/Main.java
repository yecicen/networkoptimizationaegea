
/* This code reused from https://rosettacode.org/wiki/Dijkstra%27s_algorithm */
/* Please see the origin of the code from the link above @yecicen*/


import java.util.*;

public class Main {
    private static final Graph.Edge[] GRAPH = {
            new Graph.Edge("izmir", "manisa", 39),
            new Graph.Edge("manisa", "izmir", 39),
            new Graph.Edge("izmir", "usak", 216),
            new Graph.Edge("usak", "izmir", 216),
            new Graph.Edge("izmir", "denizli", 255),
            new Graph.Edge("denizli", "izmir", 255),
            new Graph.Edge("izmir", "aydin", 180),
            new Graph.Edge("aydin", "izmir", 180),
            new Graph.Edge("manisa", "kutahya", 366),
            new Graph.Edge("kutahya", "manisa", 366),
            new Graph.Edge("manisa", "usak", 194),
            new Graph.Edge("usak", "manisa", 194),
            new Graph.Edge("manisa", "denizli", 233),
            new Graph.Edge("denizli", "manisa", 233),
            new Graph.Edge("aydin", "denizli", 103),
            new Graph.Edge("denizli", "aydin", 103),
            new Graph.Edge("aydin", "mugla", 136),
            new Graph.Edge("mugla", "aydin", 136),
            new Graph.Edge("mugla", "denizli", 113),
            new Graph.Edge("denizli", "mugla", 113),
            new Graph.Edge("denizli", "manisa", 233),
            new Graph.Edge("manisa", "denizli", 233),
            new Graph.Edge("denizli", "usak", 169),
            new Graph.Edge("usak", "denizli", 169),
            new Graph.Edge("denizli", "afyon", 229),
            new Graph.Edge("afyon", "denizli", 229),
            new Graph.Edge("usak", "afyon", 111),
            new Graph.Edge("afyon", "usak", 111),
            new Graph.Edge("usak", "kutahya", 141),
            new Graph.Edge("kutahya", "usak", 141),
            new Graph.Edge("afyon", "kutahya", 96),
            new Graph.Edge("kutahya", "afyon", 96),
    };
    private static final String START = "izmir";
    private static final String END = "kutahya";

    public static void main(String[] args) {
        Graph g = new Graph(GRAPH);
        System.out.println("Shortest path from "+START+" to "+END+" is: ");
        g.dijkstra(START);
        g.printPath(END);
    }
}

class Graph {
    private final Map<String, Vertex> graph;

    public static class Edge {
        public final String v1, v2;
        public final int dist;
        public Edge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    public static class Vertex implements Comparable<Vertex>{
        public final String name;
        public int dist = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
        public Vertex previous = null;
        public final Map<Vertex, Integer> neighbours = new HashMap<>();

        public Vertex(String name)
        {
            this.name = name;
        }

        private void printPath()
        {
            if (this == this.previous)
            {
                System.out.printf("%s", this.name);
            }
            else if (this.previous == null)
            {
                System.out.printf("%s(unreached)", this.name);
            }
            else
            {
                this.previous.printPath();
                System.out.printf(" -> %s(%d)", this.name, this.dist);
            }
        }

        public int compareTo(Vertex other)
        {
            if (dist == other.dist)
                return name.compareTo(other.name);

            return Integer.compare(dist, other.dist);
        }

        @Override public String toString()
        {
            return "(" + name + ", " + dist + ")";
        }
    }

    /** Builds a graph from a set of edges */
    public Graph(Edge[] edges) {
        graph = new HashMap<>(edges.length);

        //one pass to find all vertices
        for (Edge e : edges) {
            if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
            if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
        }

        //another pass to set neighbouring vertices
        for (Edge e : edges) {
            graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
            //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
        }
    }

    /** Runs dijkstra using a specified source vertex */
    public void dijkstra(String startName) {
        if (!graph.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
            return;
        }
        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<>();

        // set-up vertices
        for (Vertex v : graph.values()) {
            v.previous = v == source ? source : null;
            v.dist = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }

        dijkstra(q);
    }

    /** Implementation of dijkstra's algorithm using a binary heap. */
    private void dijkstra(final NavigableSet<Vertex> q) {
        Vertex u, v;
        while (!q.isEmpty()) {

            u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
            if (u.dist == Integer.MAX_VALUE) break; // we can ignore u (and any other remaining vertices) since they are unreachable

            //look at distances to each neighbour
            for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
                v = a.getKey(); //the neighbour in this iteration

                final int alternateDist = u.dist + a.getValue();
                if (alternateDist < v.dist) { // shorter path to neighbour found
                    q.remove(v);
                    v.dist = alternateDist;
                    v.previous = u;
                    q.add(v);
                }
            }
        }
    }

    /** Prints a path from the source to the specified vertex */
    public void printPath(String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
            return;
        }

        graph.get(endName).printPath();
        System.out.println();
    }
    /** Prints the path from the source to every vertex (output order is not guaranteed) */
    public void printAllPaths() {
        for (Vertex v : graph.values()) {
            v.printPath();
            System.out.println();
        }
    }
}