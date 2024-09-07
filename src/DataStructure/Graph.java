    package DataStructure;

    import org.w3c.dom.ls.LSInput;

    import java.util.*;

    // Directed graph
    public class Graph {
        private final Map<Integer, List<Edge>> adjList;
        private final Map<Integer, List<Edge>> reverseAdjList;
        private final boolean isDirected; // 新增标志位，指示图是否为有向图

        public Graph(boolean isDirected) {
            this.adjList = new HashMap<>();
            this.reverseAdjList = new HashMap<>();
            this.isDirected = isDirected;
        }

        public void addVertex(int vertex) {
            adjList.putIfAbsent(vertex, new ArrayList<>());
            reverseAdjList.putIfAbsent(vertex, new ArrayList<>());
        }

        public void addEdge(int fromVertex, int toVertex, int weight) {
            adjList.putIfAbsent(fromVertex, new ArrayList<>());
            adjList.putIfAbsent(toVertex, new ArrayList<>());
            adjList.get(fromVertex).add(new Edge(fromVertex, toVertex, weight));

            if (!isDirected) {
                // 如果是无向图，添加反向边
                adjList.get(toVertex).add(new Edge(toVertex, fromVertex, weight));
            } else {
                // 有向图才需要维护一个提供反向遍历功能的反向邻接表
                reverseAdjList.putIfAbsent(fromVertex, new ArrayList<>());
                reverseAdjList.putIfAbsent(toVertex, new ArrayList<>());
                reverseAdjList.get(toVertex).add(new Edge(toVertex, fromVertex, weight));
            }
        }

        // 返回传入节点的所有邻居
        public Iterable<Edge> neighbors(int vertex) {
            List<Edge> neighbors = adjList.getOrDefault(vertex, new ArrayList<>());
            neighbors.sort(Comparator.comparingInt(p -> p.toVertex)); // ensure order
            return neighbors;
        }

        public Iterable<Edge> reverseNeighbors(int vertex) {
            List<Edge> reverseNeighbors = reverseAdjList.getOrDefault(vertex, new ArrayList<>());
            reverseNeighbors.sort(Comparator.comparingInt(p -> p.toVertex)); // ensure order
            return reverseNeighbors;
        }

        // 返回所有节点
        public Set<Integer> getVertices() {
            return adjList.keySet();
        }

        // 返回所有边
        public List<Edge> getEdges() {
            Set<Edge> edgeSet = new HashSet<>();
            for (int vertex : adjList.keySet()) {
                for (Edge edge : adjList.get(vertex)) {
                    if (isDirected || vertex < edge.toVertex()) {
                        edgeSet.add(new Edge(vertex, edge.toVertex(), edge.weight()));
                    }
                }
            }
            return new ArrayList<>(edgeSet);
        }

        // 为了实现有权重的图的最短路径寻找，定义Edge(fromVertex, toVertex, weight)，这是一个类
        public record Edge(int fromVertex, int toVertex, int weight) {
        }
    }

