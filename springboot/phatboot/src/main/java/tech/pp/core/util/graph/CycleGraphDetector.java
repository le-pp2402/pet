package tech.pp.core.util.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CycleGraphDetector<T> {
    private HashSet<T> nodes;
    private List<Edge<T>> edges;
    private HashMap<T, Integer> visited; // 0 = unvisited, 1 = visiting, 2 = visited

    public CycleGraphDetector(List<Edge<T>> cEdges) {
        this.nodes = new HashSet<>();
        this.edges = new ArrayList<Edge<T>>();
        this.visited = new HashMap<>();

        for (Edge<T> edge : cEdges) {
            this.edges.add(edge);
            if (!nodes.contains(edge.from)) {
                nodes.add(edge.from);
            }
            if (!nodes.contains(edge.to)) {
                nodes.add(edge.to);
            }
        }

        this.edges = cEdges;
    }

    public boolean hasCycle() {
        for (T node : nodes) {
            if (visited.get(node) == null || visited.get(node) == 0) {
                if (dfs(node)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(T node) {
        visited.put(node, 1);

        for (Edge<T> edge : edges) {
            if (edge.from.equals(node)) {
                T neighbor = edge.to;
                if (visited.get(neighbor) != null && visited.get(neighbor) == 1) {
                    return true;
                }
                if (visited.get(neighbor) == null || visited.get(neighbor) == 0) {
                    if (dfs(neighbor)) {
                        return true;
                    }
                }
            }
        }

        visited.put(node, 2);
        return false;
    }

}
