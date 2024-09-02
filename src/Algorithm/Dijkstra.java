package Algorithm;

import DataStructure.Graph;
import com.sun.jdi.InvalidTypeException;

import static DataStructure.Graph.Edge;

import java.util.*;

public class Dijkstra {
    private final Map<Integer, Integer> distTo; // 距离表，存储从起点到每个节点的最短已知距离
    private final Map<Integer, Integer> edgeTo; // 父节点映射，存储每个节点在已知最短路径上的前一个节点
    private final Queue<VertexDistancePair> pq; // 优先级队列，用于选择当前距离最短的节点
    private final int start;

    public Dijkstra(Graph graph, int start) {
        this.distTo = new HashMap<>();
        this.edgeTo = new HashMap<>();
        this.start = start;
        pq = new PriorityQueue<>(Comparator.comparingInt(pair -> pair.distance));

        // 初始化起点
        distTo.put(start, 0);
        pq.add(new VertexDistancePair(start, 0));

        // 初始化其他所有节点的距离为无穷大
        for (int vertex : graph.getVertices()) {
            if (vertex != start) {
                distTo.put(vertex, Integer.MAX_VALUE);
            }
        }

        while (!pq.isEmpty()) {
            VertexDistancePair current = pq.poll();
            int vertex = current.vertex;

            // 松弛所有邻居节点
            for (Edge edge : graph.neighbors(vertex)) {
                relax(edge, vertex);
            }
        }
    }

    // relaxation, 更新从 vertex 到 edge.toVertex() 的最短路径
    private void relax(Edge edge, int vertex) {
        int toVertex = edge.toVertex();
        int weight = edge.weight();

        if (distTo.get(vertex) + weight < distTo.get(toVertex)) {
            distTo.put(toVertex, distTo.get(vertex) + weight);
            edgeTo.put(toVertex, vertex);

            // 如果优先队列中已经有这个节点，更新它的优先级
            pq.removeIf(pair -> pair.vertex == toVertex); // 移除旧的距离
            pq.add(new VertexDistancePair(toVertex, distTo.get(toVertex))); // 插入新的距离
        }
    }

    // 返回从起点到指定节点的最短路径距离
    public int distTo(int vertex) {
        return distTo.getOrDefault(vertex, Integer.MAX_VALUE);
    }

    // 返回从起点到指定节点的路径
    public List<Integer> pathTo(int vertex) {
        if (!hasPathTo(vertex)) return null;

        List<Integer> path = new ArrayList<>();
        for (int x = vertex; x != start; x = edgeTo.get(x)) {
            path.add(x);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    // 判断是否存在从起点到目标节点的路径
    public boolean hasPathTo(int vertex) {
        return distTo.get(vertex) < Integer.MAX_VALUE;
    }

    // 优先级队列pq中的元素
    private record VertexDistancePair(int vertex, int distance) {
    }
}
