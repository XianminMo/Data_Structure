    package DataStructure;

    import java.util.*;

    // Directed graph
    public class Graph {
        private final Map<Integer, List<Edge>> adjList;
        private final Map<Integer, List<Edge>> reverseAdjList;

        public Graph() {
            this.adjList = new HashMap<>();
            this.reverseAdjList = new HashMap<>();
        }

        public void addVertex(int vertex) {
            adjList.putIfAbsent(vertex, new ArrayList<>());
            reverseAdjList.putIfAbsent(vertex, new ArrayList<>());
        }

        public void addEdge(int fromVertex, int toVertex, int weight) {
            adjList.putIfAbsent(fromVertex, new ArrayList<>());
            adjList.putIfAbsent(toVertex, new ArrayList<>());
            adjList.get(fromVertex).add(new Edge(toVertex, weight));
            reverseAdjList.putIfAbsent(fromVertex, new ArrayList<>());
            reverseAdjList.putIfAbsent(toVertex, new ArrayList<>());
            reverseAdjList.get(toVertex).add(new Edge(fromVertex, weight));
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

        // 为了实现有权重的图的最短路径寻找，定义Edge(toVertex, weight)，这是一个类
        public record Edge(int toVertex, int weight) {
        }
    }

