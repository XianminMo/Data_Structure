package Algorithm.MST;

import DataStructure.Graph;
import DataStructure.Graph.Edge;
import DataStructure.UnionFind;
import java.util.*;

public class KruskalMST {
    private final List<Edge> mstEdges;  // 最小生成树的边集
    private int totalWeight;  // 最小生成树的总权重

    public KruskalMST(Graph graph) {
        mstEdges = new ArrayList<>();
        totalWeight = 0;

        // 获取图中的所有边
        List<Edge> edges = graph.getEdges();
        // 按权重升序排序
        edges.sort(Comparator.comparingInt(Edge::weight));

        // 创建并查集，初始化时每个节点都是一个独立的集合
        UnionFind uf = new UnionFind(graph.getVertices().size());

        // 依次处理每条边，按权重从小到大
        for (Edge edge : edges) {
            int fromVertex = edge.fromVertex();
            int toVertex = edge.toVertex();

            // 检查两个顶点是否已经连通（即是否在同一个集合中）
            if (!uf.connected(fromVertex, toVertex)) {
                // 如果没有连通，则将这条边加入到最小生成树中
                uf.union(fromVertex, toVertex);
                mstEdges.add(edge);
                totalWeight += edge.weight();
            }
        }
    }

    // 返回最小生成树的边集
    public List<String> getMSTEdges() {
        List<String> edges = new ArrayList<>();
        for (Edge edge : mstEdges) {
            edges.add(edge.fromVertex() + " - " + edge.toVertex() + " (weight: " + edge.weight() + ")");
        }
        return edges;
    }

    // 返回最小生成树的总权重
    public int getTotalWeight() {
        return totalWeight;
    }
}
