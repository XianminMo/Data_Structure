import Algorithm.MST.KruskalMST;
import Algorithm.MST.PrimMST;
import Algorithm.ShortestPath.Dijkstra;
import DataStructure.Graph;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(false);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 11);
        graph.addEdge(1, 4, 3);
        graph.addEdge(2, 5, 15);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 2, 1);
        graph.addEdge(4, 5, 4);
        graph.addEdge(4, 6, 5);
        graph.addEdge(6, 3, 1);
        graph.addEdge(6, 5, 1);


        Dijkstra dijkstra = new Dijkstra(graph, 0);
        List<Integer> path = dijkstra.pathTo(3);
        System.out.println("Path from 0 to 3: " + path);
        System.out.println("Distance from 0 to 3: " + dijkstra.distTo(3));



        PrimMST prim = new PrimMST(graph, 0);
        System.out.println("Edges in MST: ");
        for (String edge : prim.getMSTEdges()) {
            System.out.println(edge);
        }
        System.out.println("Total weight of MST: " + prim.getTotalWeight());


        KruskalMST kruskal = new KruskalMST(graph);
        System.out.println("Edges in MST: ");
        for (String edge : kruskal.getMSTEdges()) {
            System.out.println(edge);
        }
        System.out.println("Total weight of MST: " + kruskal.getTotalWeight());
    }
}