package com.squareroot.util;

public class MinHeap {
    private Point[] Heap;
    private int size;
    private int maxsize;

    private static final int FRONT = 1;

    public MinHeap(int maxsize) {
	this.maxsize = maxsize;
	this.size = 0;
	Heap = new Point[this.maxsize + 1];
    }

    // Function to return the position of
    // the parent for the node currently
    // at pos
    private int parent(int pos) {
	return pos / 2;
    }

    // Function to return the position of the
    // left child for the node currently at pos
    private int leftChild(int pos) {
	return (2 * pos);
    }

    // Function to return the position of
    // the right child for the node currently
    // at pos
    private int rightChild(int pos) {
	return (2 * pos) + 1;
    }

    // Function that returns true if the passed
    // node is a leaf node
    private boolean isLeaf(int pos) {
	if (pos >= (size / 2) && pos <= size) {
	    return true;
	}
	return false;
    }

    // Function to swap two nodes of the heap
    private void swap(int fpos, int spos) {
	Point tmp;
	tmp = Heap[fpos];
	Heap[fpos] = Heap[spos];
	Heap[spos] = tmp;
    }

    // Function to heapify the node at pos
    private void minHeapify(int pos) {

	// If the node is a non-leaf node and greater
	// than any of its child
	if (!isLeaf(pos)) {
	    if (Heap[pos].getD() > Heap[leftChild(pos)].getD() || Heap[pos].getD() > Heap[rightChild(pos)].getD()) {

		// Swap with the left child and heapify
		// the left child
		if (Heap[leftChild(pos)].getD() < Heap[rightChild(pos)].getD()) {
		    swap(pos, leftChild(pos));
		    minHeapify(leftChild(pos));
		}

		// Swap with the right child and heapify
		// the right child
		else {
		    swap(pos, rightChild(pos));
		    minHeapify(rightChild(pos));
		}
	    }
	}
    }

    // Function to insert a node into the heap
    public void insert(Point element) {
	if (size >= maxsize) {
	    return;
	}
	Heap[++size] = element;
	int current = size;

	while (current > 1 && Heap[current].getD() < Heap[parent(current)].getD()) {
	    swap(current, parent(current));
	    current = parent(current);
	}
    }

    public void att(Point p) {
	int c = 0;
	for (Point pp : Heap) {
	    if (pp != null && p.compareTo(pp) == 0) {
		Heap[c] = p;
		break;
	    }
	    c++;
	}
	heapifyUP(c);
    }

    private void heapifyUP(int pos) {
	if (Heap[pos].getD() < Heap[parent(pos)].getD()) {
	    swap(pos, parent(pos));
	    heapifyUP(parent(pos));
	}

    }

    // Function to print the contents of the heap
    public void print() {
	for (int i = 1; i <= size / 2; i++) {
	    System.out.print(
		    " PARENT : " + Heap[i] + " LEFT CHILD : " + Heap[2 * i] + " RIGHT CHILD :" + Heap[2 * i + 1]);
	    System.out.println();
	}
    }

    // Function to build the min heap using
    // the minHeapify
    public void minHeap() {
	for (int pos = (size / 2); pos >= 1; pos--) {
	    minHeapify(pos);
	}
    }

    // Function to remove and return the minimum
    // element from the heap
    public Point remove() {
	Point popped = Heap[FRONT];
	Heap[FRONT] = Heap[size];
	minHeapify(FRONT);
	size--;
	return popped;
    }

    // Driver code

}
