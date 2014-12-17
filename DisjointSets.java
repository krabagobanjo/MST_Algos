import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class DisjointSets<T> implements DisjointSetsInterface<T> {
    //Should be a map of data to its parent; root data maps to itself.
    private Map<T, Node> set;

    /**
     * @param setItems the initial items (sameSet and merge will never be called
     * with items not in this set, this set will never contain null elements).
     */
    public DisjointSets(Set<T> setItems) {
        set = new HashMap<T, Node>();
        for (T i : setItems) {
            set.put(i, new Node(i));
        }
    }

    @Override
    public boolean sameSet(T u, T v) {
        Node a = set.get(u);
        Node b = set.get(v);
        return a.sameRoot(b);
    }

    @Override
    public void merge(T u, T v) {
        if (sameSet(u, v)) {
            return;
        }
        Node a = set.get(u);
        Node b = set.get(v);
        T temp;
        if (a.getRank() == b.getRank()) {
            temp = a.getRoot();
            a.setRoot(b.getRoot());
            b.setRank(b.getRank() + 1);
            for (T i : set.keySet()) {
                if (set.get(i).getRoot().equals(temp)) {
                    set.get(i).setRoot(b.getRoot());
                }
            }
        } else {
            if (a.getRank() < b.getRank()) {
                temp = a.getRoot();
                a.setRoot(b.getRoot());
                for (T i : set.keySet()) {
                    if (set.get(i).getRoot().equals(temp)) {
                        set.get(i).setRoot(b.getRoot());
                    }
                }
            } else {
                temp = b.getRoot();
                b.setRoot(a.getRoot());
                for (T i : set.keySet()) {
                    if (set.get(i).getRoot().equals(temp)) {
                        set.get(i).setRoot(a.getRoot());
                    }
                }
            }
        }
    }

    private class Node {
        //Fill in whatever methods or variables you believe are needed by node
        //here.  Should be O(1) space. This means no arrays, data structures,
        //etc.
        private T root;
        private int rank;
        public Node(T root) {
            this.root = root;
            rank = 0;
        }
        public int getRank() {
            return rank;
        }
        public void setRank(int rank) {
            this.rank = rank;
        }
        public T getRoot() {
            return root;
        }
        public void setRoot(T root) {
            this.root = root;
        }
        public boolean sameRoot(Node n) {
            //boolean a = this.root.hashCode() == n.getRoot().hashCode();
            //boolean b = this.rank == n.getRank();
            return this.root.hashCode() == n.getRoot().hashCode();
        }
    }
}
