import java.util.*;

public class HuffmanTree {
	// DO NOT include the frequency or priority in the tree
	private class Node {
		private Node left;
		private char data;
		private Node right;
		private Node parent;

		private Node(Node L, char d, Node R, Node P) {
			left = L;
			data = d;
			right = R;
			parent = P;
		}
	}

	// this value is changed by the move methods
	private Node root;
	private Node current;

	public HuffmanTree() {
		root = null;
		current = null;
	}

	public HuffmanTree(char d) {
		// makes a single node tree
		root = new Node(null, d, null, null);
	}

	public HuffmanTree(String t, char nonLeaf) {
		// Assumes t represents a post order representation of the tree as discussed
		// in class
		// nonLeaf is the char value of the data in the non-leaf nodes
		// in class we used (char) 128 for the non-leaf value
		Stack<HuffmanTree> huffmanStack = new Stack<>();

		for (int i = 0; i < t.length(); i++) {
			if (t.charAt(i) != nonLeaf) {

				huffmanStack.push(new HuffmanTree(t.charAt(i)));
			} else {

				HuffmanTree rightTree = huffmanStack.pop();
				HuffmanTree leftTree = huffmanStack.pop();

				HuffmanTree tree = new HuffmanTree(leftTree, rightTree, nonLeaf);
				huffmanStack.push(tree);
			}

		}

		HuffmanTree remaining = huffmanStack.pop();

		root = new Node(remaining.root.left, (char) 128, remaining.root.right, null);

	}

	public HuffmanTree(HuffmanTree b1, HuffmanTree b2, char d) {
		// makes a new tree where b1 is the left subtree and b2 is the right subtree
		// d is the data in the root
		b1.root.parent = root;
		b2.root.parent = root;
		root = new Node(b1.root, d, b2.root, null);

	}

	// use the move methods to traverse the tree
	// the move methods change the value of current
	// use these in the decoding process
	public void moveToRoot() {
		// change current to reference the root of the tree
		current = root;
	}

	public void moveToLeft() {
		// PRE: the current node is not a leaf
		// change current to reference the left child of the current node
		current = current.left;
	}

	public void moveToRight() {
		// PRE: the current node is not a leaf
		// change current to reference the right child of the current node
		current = current.right;
	}

	public void moveToParent() {
		// PRE: the current node is not the root
		// change current to reference the parent of the current node
		current = current.parent;
	}

	public boolean atRoot() {
		// returns true if the current node is the root otherwise returns false
		return (current.parent == null);
	}

	public boolean atLeaf() {
		// returns true if current references a leaf other wise returns false
		return ((current.left == null) && (current.right == null));

	}

	public char current() {
		// returns the data value in the node referenced by current
		return current.data;
	}

	public class PathIterator implements Iterator<String> {
		// the iterator returns the path (a series of 0s and 1s) to each leaf
		// DO NOT compute all paths in the constructor
		// only compute them as needed (similar to what you did in homework 2)
		// add private methods and variables as needed

		Stack<Node> stack = new Stack<>();
		Stack<String> encodingStack = new Stack<>();
		String r = "";
		String encoding = "";

		private Node currNode;

		public PathIterator() {

			currNode = root;
			encoding = "";

			stack.push(root);
			encodingStack.push(encoding);

			while (currNode.left != null) {

				currNode = currNode.left;
				encoding += '1';

				stack.push(currNode);
				encodingStack.push(encoding);
			}

		}

		public boolean hasNext() {
			return (!stack.isEmpty());
		}

		public String next() {
			// create a node to remember which node was returned
			Node prevNode = currNode;

			r = "";
			currNode = stack.peek();
			encoding = encodingStack.peek();

			boolean breakLoop = false;

			do {
				// check if right node was previously returned
				if (currNode.right == prevNode && prevNode != null) {
					breakLoop = true;
				} else {
					// check if left node was returned
					if (currNode.left == prevNode && currNode.right == null) {
						breakLoop = true;
					} else {
						// goto left child
						while (currNode.left != null && prevNode != currNode.left) {
							currNode = currNode.left;
							encoding += '1';
							stack.push(currNode);
							encodingStack.push(encoding);
						}
						// goto right child
						if (currNode.right != null) {
							currNode = currNode.right;
							encoding += '0';
							stack.push(currNode);
							encodingStack.push(encoding);
						}
					}
					// if at leaf node, break from loop and return node
					if (currNode.left == null && currNode.right == null)
						breakLoop = true;
				}
			} while (!breakLoop);

			// return node on top of stack
			prevNode = stack.pop();
			encoding = encodingStack.pop();

			char c = prevNode.data;

			if (c != (char) 128) {

				r += prevNode.data;
				r += " ";

				r += encoding;
			}
			return r;
		}

		public void remove() {
			// optional method not implemented
		}

		public String toString() {
			// returns a string representation of the tree using the postorder format
			// discussed in class

			return toString(root);
		}

		private String toString(Node n) {
			String treeString = "";
			if (n == null) {
				return "";
			}

			if (n.right == null && n.left == null) {
				treeString = treeString + n.data;
			} else {
				treeString += toString(n.left);
				treeString += toString(n.right);
				treeString += (char) 128;
			}

			return treeString;
		}
	}

	public Iterator<String> iterator() {
		// return a new path iterator object
		return new PathIterator();
	}

}
