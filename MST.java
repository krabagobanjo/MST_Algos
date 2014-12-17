import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class MST {

    /**
     * Using disjoint set(s), run Kruskal's algorithm on the given graph and
     * return the MST. Return null if no MST exists for the graph.
     *
     * @param g The graph to be processed. Will never be null.
     * @return The MST of the graph; null if no valid MST exists.
     */
    public static Collection<Edge> kruskals(Graph g) {
        Collection<Edge> ret = new HashSet<Edge>();
        PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
        edges.addAll(g.getEdgeList());
        DisjointSets<Vertex> set = new DisjointSets<Vertex>(g.getVertices());
        Vertex a;
        Vertex b;
        Edge curr;
        while (edges.peek() != null) {
            curr = edges.poll();
            a = curr.getU();
            b = curr.getV();
            if (!set.sameSet(a, b)) {
                ret.add(curr);
                set.merge(a,  b);
            }
        }
        
        return ret;
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree. If no MST exists, return null.
     *
     * @param g The graph to be processed. Will never be null.
     * @param start The ID of the start node. Will always exist in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static Collection<Edge> prims(Graph g, int start) {
        Collection<Edge> ret = new HashSet<Edge>();
        Set<Vertex> vertices = g.getVertices();
        Vertex curr = null;
        for (Vertex v : vertices) {
            if (v.getId() == start) {
                curr = v;
            }
        }
        HashSet<Vertex> visited = new HashSet<Vertex>();
        PriorityQueue<Edge> adjQ = new PriorityQueue<Edge>();
        Map<Vertex, Integer> adjMap;
        HashSet<Edge> temp = new HashSet<Edge>();
        boolean loop = true;
        while (loop) {
            adjMap = g.getAdjacencies(curr);
            for (Vertex v : adjMap.keySet()) {
                adjQ.add(new Edge(curr, v, adjMap.get(v)));
            }
            if (!visited.contains(adjQ.peek().getV())) {
                visited.add(adjQ.peek().getV());
                curr = adjQ.peek().getV();
                ret.add(adjQ.poll());
            } else {
                while (adjQ.peek() != null && visited.contains(adjQ.peek().getV())) {
                    temp.add(adjQ.poll());
                }
                if (adjQ.peek() != null) {
                    visited.add(adjQ.peek().getV());
                    curr = adjQ.peek().getV();
                    ret.add(adjQ.remove());
                } else {
                    loop = false;
                }
                adjQ.addAll(temp);
                temp.clear();
            }
        }
        return ret;
    }
}
