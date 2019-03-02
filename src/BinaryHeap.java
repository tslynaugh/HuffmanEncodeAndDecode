public class BinaryHeap {
	// implements a binary heap where the heap rule is the value in the parent
	// node is less than
	// or equal to the values in the child nodes
	// the implementation uses parallel arrays to store the priorities and the
	// trees
	// you must use this implementation
	int priority[];
	HuffmanTree trees[];
	int size;

	public BinaryHeap(int s) {
		priority = new int[s + 1];
		trees = new HuffmanTree[s + 1];
		size = 0;
	}

	public void removeMin() {
		// PRE: size != 0
		// removes the priority and tree at the root of the heap
		int parent;
		int child;
		int x = priority[size];
		HuffmanTree d = trees[size];
		size--;
		child = 2;
		while (child <= size) {
			parent = child / 2;
			if (child < size && priority[child + 1] < priority[child])
				child++;
			if (x < priority[child])
				break;
			priority[parent] = priority[child];
			trees[parent] = trees[child];
			child = 2 * child;
		}
		priority[child / 2] = x;
		trees[child / 2] = d;
	}

	public int getMinPriority() {
		// PRE: size != 0
		// return the priority in the root of the heap
		return priority[1];
	}

	public HuffmanTree getMinTree() {
		// PRE: size != 0
		// return the tree in the root of the heap
		return trees[1];
	}

	public boolean full() {
		// return true if the heap is full otherwise return false
		return size == priority.length - 1;
	}

	public boolean empty() {
		return size == 0;
	}

	public void insert(int p, HuffmanTree t) {
		// insert the priority p and the associated tree t into the heap
		// PRE !full()
		int parent;
		int child;
		size++;
		child = size;
		parent = child / 2;
		priority[0] = p;
		trees[0] = t;
		while (priority[parent] > p) {
			priority[child] = priority[parent];
			trees[child] = trees[parent];
			child = parent;
			parent = child / 2;

		}
		priority[child] = p;
		trees[child] = t;

	}

	public int getSize() {
		// return the number of values (priority , tree) pairs in the heap
		return size;
	}
}
