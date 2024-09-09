package DataStructure;

import java.util.Arrays;

/**
 * Simple Union-Find (Disjoint Set) data structure with path compression and union by size.
 * It efficiently supports union and find operations.
 */
public class UnionFind {
    private final int[] ufSet; // Array representing the Union-Find structure

    /**
     * Creates a UnionFind data structure holding N items. Initially, all items are in disjoint sets.
     * Each item is its own root, and the size of each set is 1 (indicated by -1).
     *
     * @param N Number of items
     */
    public UnionFind(int N) {
        ufSet = new int[N];
        Arrays.fill(ufSet, -1); // Initialize each item as a root of its own set with size 1
    }

    /**
     * Returns the size of the set that element V belongs to.
     * Size is stored as a negative value at the root of each set.
     *
     * @param v Element whose set size is to be determined
     * @return Size of the set containing V
     */
    public int sizeOf(int v) {
        int root = find(v); // Find the root of the set
        return -ufSet[root]; // Return the size, which is stored as a negative value
    }

    /**
     * Returns the parent of element V. If V is a root, it returns the negative size of the tree.
     *
     * @param v Element whose parent is to be returned
     * @return Parent of V, or negative size if V is a root
     */
    public int getParent(int v) {
        return ufSet[v];
    }

    /**
     * Checks if two elements are in the same set.
     *
     * @param v1 First element
     * @param v2 Second element
     * @return true if V1 and V2 are connected, false otherwise
     */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2); // They are connected if they share the same root
    }

    /**
     * Returns the root of the set containing element V. Uses path compression to flatten the structure,
     * making future operations faster. Throws an exception if V is out of bounds.
     *
     * @param v Element whose root is to be found
     * @return The root of the set containing V
     * @throws IllegalArgumentException if V is out of bounds
     */
    public int find(int v) {
        if (v < 0 || v >= ufSet.length) {
            throw new IllegalArgumentException("IllegalArgument!");
        }
        if (getParent(v) < 0) {
            return v; // V is a root if its parent value is negative
        } else {
            ufSet[v] = find(getParent(v)); // Path compression: Make the found root the direct parent of V
        }
        return getParent(v); // Return the root
    }

    /**
     * Connects two elements V1 and V2 by merging their sets. Uses union by size heuristic:
     * the smaller set (or tie-breaking V1's set) is merged into the larger set.
     *
     * @param v1 First element
     * @param v2 Second element
     */
    public void union(int v1, int v2) {
        if (connected(v1, v2)) {
            return; // If already connected, no need to union
        }
        int root1 = find(v1);
        int root2 = find(v2);

        int size1 = -ufSet[root1];
        int size2 = -ufSet[root2];

        if (size1 <= size2) {
            ufSet[root1] = root2; // Make root2 the parent of root1
            ufSet[root2] = -(size1 + size2); // Update the size of the new root (root2)
        } else {
            ufSet[root2] = root1; // Make root1 the parent of root2
            ufSet[root1] = -(size1 + size2); // Update the size of the new root (root1)
        }
    }
}
