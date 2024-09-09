package DataStructure;

/**
 * A MinPQ class that implements a minimum priority queue using a binary heap.
 * This heap is implemented as a complete binary tree stored in an array.
 *
 * @param <T> The type of elements held in this priority queue, which must be comparable.
 */
public class MinPQ<T extends Comparable<T>> {
    private T[] heap; // Array representation of the min binary heap
    private int size; // Number of elements in the heap

    /**
     * Constructs a MinPQ with the specified initial capacity.
     *
     * @param capacity The initial capacity of the priority queue.
     */
    public MinPQ(int capacity) {
        this.heap = (T[]) new Comparable[capacity + 1]; // Reserve index 0, start using from index 1
        this.size = 0;
    }

    /**
     * Constructs a MinPQ with a default capacity of 10.
     */
    public MinPQ() {
        this(10); // Default capacity is 10
    }

    /**
     * Returns the number of elements in the priority queue.
     *
     * @return The size of the priority queue.
     */
    public int size() {
        return size;
    }

    /**
     * Adds an item to the Min priority queue.
     *
     * @param item The item to add.
     */
    public void add(T item) {
        if (size == heap.length - 1) {
            resize(); // Double the array size if full
        }
        heap[++size] = item; // Insert at the end of the heap
        swimUp(size); // Restore the heap order
    }

    /**
     * Returns the smallest item in the priority queue without removing it.
     *
     * @return The smallest item, or null if the priority queue is empty.
     */
    public T peek() {
        if (size == 0) {
            return null;
        }
        return heap[1]; // The smallest item is at the root (index 1)
    }

    /**
     * Removes and returns the smallest item from the Min priority queue.
     *
     * @return The smallest item, or null if the priority queue is empty.
     */
    public T poll() {
        if (size == 0) {
            return null;
        }
        T result = heap[1]; // The root of the heap
        heap[1] = heap[size]; // Move the last item to the root
        heap[size--] = null; // Decrease size and clear the last item
        sinkDown(1); // Restore the heap order
        return result;
    }

    /**
     * Checks if the priority queue is empty.
     *
     * @return true if the priority queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Restores the heap order by swimming up the item at the given index.
     * This is used when a new item is added.
     *
     * @param index The index of the item to swim up.
     */
    private void swimUp(int index) {
        while (index > 1 && heap[parent(index)].compareTo(heap[index]) > 0) {
            swap(index, parent(index)); // Swap with parent if less than parent
            index = parent(index); // Move up to the parent's index
        }
    }

    /**
     * Restores the heap order by sinking down the item at the given index.
     * This is used when the root item is removed.
     *
     * @param index The index of the item to sink down.
     */
    private void sinkDown(int index) {
        while (leftChild(index) <= size) { // Ensure there is at least a left child
            int smallerChild = leftChild(index);
            if (rightChild(index) <= size && heap[rightChild(index)].compareTo(heap[leftChild(index)]) < 0) {
                smallerChild = rightChild(index); // Right child is smaller
            }
            if (heap[index].compareTo(heap[smallerChild]) <= 0) {
                break; // The current item is in the correct position
            }
            swap(index, smallerChild); // Swap with the smaller child
            index = smallerChild; // Move down to the child's index
        }
    }

    /**
     * Swaps the items at the two given indices in the heap.
     *
     * @param index1 The first index.
     * @param index2 The second index.
     */
    private void swap(int index1, int index2) {
        T temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    /**
     * Resizes the heap array to double its current size.
     */
    private void resize() {
        T[] newHeap = (T[]) new Comparable[heap.length * 2];
        System.arraycopy(heap, 1, newHeap, 1, size); // Copy elements starting from index 1
        heap = newHeap;
    }

    /**
     * Returns the index of the parent of the item at the given index.
     *
     * @param index The index of the item.
     * @return The index of the parent.
     */
    private int parent(int index) {
        return index / 2;
    }

    /**
     * Returns the index of the left child of the item at the given index.
     *
     * @param index The index of the item.
     * @return The index of the left child.
     */
    private int leftChild(int index) {
        return index * 2;
    }

    /**
     * Returns the index of the right child of the item at the given index.
     *
     * @param index The index of the item.
     * @return The index of the right child.
     */
    private int rightChild(int index) {
        return index * 2 + 1;
    }

    /**
     * Main method to test the MinPQ functionality.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        MinPQ<Integer> pq = new MinPQ<>();

        pq.add(10);
        pq.add(5);
        pq.add(15);
        pq.add(3);

        // Poll elements to verify they are returned in sorted order
        while (!pq.isEmpty()) {
            System.out.println(pq.poll()); // Expected output: 3, 5, 10, 15
        }
    }
}

