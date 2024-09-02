package DataStructure;

import java.util.Map;

public class MinPQ <T extends Comparable<T>>{
    private T[] heap; // 用一个数组表示一个最小二叉堆（min and complete）
    private int size;

    public MinPQ(int capacity) {
        this.heap = (T[])new Comparable[capacity + 1]; // 保留索引0空位
        this.size = 0;
    }

    public MinPQ() {
        this(10);
    }

    // Returns the size of the Min priority queue
    public int size() {
        return size;
    }

    // Adds the item to the Min priority queue
    public void add(T item) {
        if (size == heap.length - 1) {
            resize();
        }
        heap[size + 1] = item;
        swimUp(size + 1);
        size++;
    }

    // Return the smallest(first) item in the Min priority queue
    public T peek() {
        if (size == 0) {
            return null;
        }
        return heap[1];
    }

    // Removes the smallest item from the Min priority queue
    public T poll() {
        if (size == 0) {
            return null;
        }
        T reslut = heap[1];
        heap[1] = heap[size];
        heap[size] = null;
        size--;
        sinkDown(1);
        return reslut;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Helper method */
    private void swimUp(int index) {
        if (index == 1) {
            return;
        }
        if (heap[parent(index)].compareTo(heap[index]) > 0) {
            swap(index, parent(index));
            swimUp(parent(index));
        }
    }

    private void sinkDown(int index) {
        int leftChild = leftChild(index);
        int rightChild = rightChild(index);
        if (leftChild > size) {
            return;
        }
        int smallerChild = leftChild;

        if (rightChild <= size && heap[rightChild].compareTo(heap[leftChild]) < 0) {
            smallerChild = rightChild;
        }

        if (heap[smallerChild].compareTo(heap[index]) < 0) {
            swap(index, smallerChild);
            sinkDown(smallerChild);
        }
    }

    private void swap(int index, int parent) {
        T temp = heap[index];
        heap[index] = heap[parent];
        heap[parent] = temp;
    }

    private int parent(int index) {
        return index / 2;
    }

    private void resize() {
        T[] newHeap = (T[]) new Comparable[heap.length * 2];
        System.arraycopy(heap, 1, newHeap, 1, size); // 从索引1开始复制
        heap = newHeap;
    }

    private int rightChild(int index) {
        return index * 2 + 1;
    }

    private int leftChild(int index) {
        return index * 2;
    }

    public static void main(String[] args) {
        MinPQ<Integer> pq = new MinPQ<>();

        pq.add(10);
        pq.add(5);
        pq.add(15);
        pq.add(3);

        while (!pq.isEmpty()) {
            System.out.println(pq.poll()); // 输出 3, 5, 10, 15
        }
    }
}
