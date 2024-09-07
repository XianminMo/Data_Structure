package Algorithm.MST;
import DataStructure.Graph;
import static DataStructure.Graph.Edge;
import java.util.*;

public class PrimMST {
    private final Queue<VertexWeightPair> pq;
    private final int start;
    private final Map<Integer, Integer> weightTo;
    private final Map<Integer, Integer> edgeTo;
    private final Set<Integer> marked;

    public PrimMST(Graph graph, int start) {
        this.pq = new PriorityQueue<>(Comparator.comparingInt(pair -> pair.weight));
        this.start = start;
        this.weightTo = new HashMap<>();
        this.edgeTo = new HashMap<>();
        this.marked = new HashSet<>();

        // 初始化节点
        weightTo.put(start, 0);
        pq.add(new VertexWeightPair(start, 0));


        // Prim
        while (!pq.isEmpty()) {
            VertexWeightPair current = pq.poll();
            int vertex = current.vertex;

            // 如果该节点已经被处理，跳过
            if (marked.contains(vertex)) continue;
            marked.add(vertex);

            // scan所有邻接节点
            for (Edge edge : graph.neighbors(vertex)) {
                relax(edge, vertex);
            }
        }
    }

    private void relax(Edge edge, int vertex) {
        int toVertex = edge.toVertex();
        int weight = edge.weight();

        // 如果 weightTo 中没有 toVertex，则初始化它为 Integer.MAX_VALUE
        if (!weightTo.containsKey(toVertex)) {
            weightTo.put(toVertex, Integer.MAX_VALUE);
        }

        if (!marked.contains(toVertex) && weight < weightTo.get(toVertex)) {
            weightTo.put(toVertex, weight);
            edgeTo.put(toVertex, vertex);

            pq.add(new VertexWeightPair(toVertex, weightTo.get(toVertex)));
        }
    }

    // 返回MST的边集
    public List<String> getMSTEdges() {
        List<String> edges = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : edgeTo.entrySet()) {
            int to = entry.getKey();
            int from = entry.getValue();
            edges.add(from + " - " + to + " (weight: " + weightTo.get(to) + ")");
        }
        return edges;
    }

    // 返回MST的总权重
    public int getTotalWeight() {
        return weightTo.values().stream().mapToInt(Integer::intValue).sum();
    }

    private record VertexWeightPair(int vertex, int weight) {
    }
}
