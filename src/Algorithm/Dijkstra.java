package Algorithm;

import DataStructure.Graph;
import static DataStructure.Graph.Edge;

import java.util.*;

public class Dijkstra {
    private final Map<Integer, Integer> distTo; // 距离表，存储从起点到每个节点的最短已知距离
    private final Map<Integer, Integer> edgeTo; // 父节点映射，存储每个节点在已知最短路径上的前一个节点
    private final Queue<VertexDistancePair> pq; // 优先级队列，用于选择当前距离最短的节点

    public Dijkstra(Graph graph, int start) {
        this.distTo = new HashMap<>();
        this.edgeTo = new HashMap<>();
        pq = new PriorityQueue<>(Comparator.comparingInt(pair -> pair.distance));

        // 初始化起点
        distTo.put(start, 0);
        pq.add(new VertexDistancePair(start, 0));

        // 初始化其他所有节点的距离为无穷大
        for (int vertex : )
    }

    private static class VertexDistancePair {
        private int vertex;
        private int distance;

        public VertexDistancePair(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}
