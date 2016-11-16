package spellchecker;

import java.util.*;

public class BinaryTree {
	private BinaryTreeNode root;

	private int count = 0;


	public BinaryTree() {

	}


	public BinaryTree(String[] words) {
		for (String word : words) {
			add(word);
		}
	}


	public BinaryTree(ArrayList<String> words) {
		for (String word : words) {
			add(word);
		}
	}


	/**
	 * Checks if word1 is lexigraphically prior to word2.
	 * 
	 * @param word1
	 * @param word2
	 * @return True if word1 comes before word2, false if word1 comes after word2
	 * @throws IllegalArgumentException
	 *             if the words are equal.
	 */
	private static boolean areWordsInOrder(String word1, String word2) throws IllegalArgumentException {
		int compare = word1.compareTo(word2);

		if (compare < 0) {
			// word1 comes before word2
			return true;
		} else if (compare > 0) {
			// word1 comes after word2
			return false;
		} else {
			throw new IllegalArgumentException(String.format("Words are equal: %s, %s.", word1, word2));
		}
	}


	public void add(String word) throws IllegalArgumentException {
		count++;

		word = word.trim().toLowerCase();

		if (root == null) {
			root = new BinaryTreeNode(word);
			return;
		}

		BinaryTreeNode cur = root;

		int maxDepth = 100;
		for (int i = 0; i < maxDepth; i++) {
			if (areWordsInOrder(word, cur.value)) {
				if (cur.left != null) {
					cur = cur.left;
					continue;
				} else {
					cur.left = new BinaryTreeNode(word);
					// out.println("Word: " + word + ", Depth: " + i);
					return;
				}
			} else {
				if (cur.right != null) {
					cur = cur.right;
					continue;
				} else {
					cur.right = new BinaryTreeNode(word);
					// out.println("Word: " + word + ", Depth: " + i);
					return;
				}
			}
		}

		throw new RuntimeException("The word '" + word + "' required a depth " + "greater than " + maxDepth
				+ ". There are " + count + " words already in the tree.");

	}


	/**
	 * @param word
	 * @return If the word is <b>found</b> this method returns <b>null</b>. Otherwise, it returns a String array
	 *         organized as follows:
	 * 
	 *         <pre>
	 *         [0] = Preceding word in the dictionary 
	 *         [1] = Succeeding word in the dictionary 
	 *         
	 *              e.g. if the unknown word was "spelm", the result might be:
	 *              
	 *         [0] = "spells" 
	 *         [1] = "spelt"
	 *         
	 *         If there is no preceding or succeeding word in the dictionary, set the element to "".
	 *         </pre>
	 */
	public String[] find(String word) {
		word = word.trim().toLowerCase();

		BinaryTreeNode cur = root;

		Stack<BinaryTreeNode> rightParents = new Stack<>();
		Stack<BinaryTreeNode> leftParents = new Stack<>();

		while (cur != null) {

			if (word.equals(cur.value)) {
				// Item is found!
				return null;
			}

			if (areWordsInOrder(word, cur.value)) {
				// word comes lexigraphically before cursor

				if (cur.left != null) {
					rightParents.push(cur);
					cur = cur.left;
				} else {
					// The target word is not found and the cursor word is after the targetword, so
					// return the cursor word and the next word to the left of the
					// cursor
					BinaryTreeNode previousNode = findPrevious(cur, leftParents);
					String leftOfTarget = (previousNode == null) ? "" : previousNode.value;
					String rightOfTarget = cur.value;
					String[] sa = { leftOfTarget, rightOfTarget };
					return sa;
				}
			} else {
				// word comes lexigraphically after cursor

				if (cur.right != null) {
					leftParents.push(cur);
					cur = cur.right;
				} else {
					// word not found, cursor is before word, so add the cursor, and the word to the right of the cursor
					String leftOfTarget = cur.value;
					BinaryTreeNode nextNode = findNext(cur, rightParents);
					String rightOfTarget = (nextNode == null) ? "" : nextNode.value;
					String[] sa = { leftOfTarget, rightOfTarget };
					return sa;
				}
			}
		}

		throw new RuntimeException("Programming Error: this line should not be reached.");
	}


	public ArrayList<String> transverseInPreorder() {
		ArrayList<String> list = new ArrayList<>();
		Stack<BinaryTreeNode> rightBranchesStack = new Stack<>();

		BinaryTreeNode cur = root;
		while (cur != null) {
			list.add(cur.value);

			// If there is a right branch to the current node, add it to the stack
			if (cur.right != null) {
				rightBranchesStack.add(cur.right);
			}

			if (cur.left != null) {
				cur = cur.left;
			} else if (!rightBranchesStack.isEmpty()) {
				cur = rightBranchesStack.pop();
			} else {
				return list;
			}
		}

		throw new RuntimeException("Programming error. This line should not be reached.");
	}


	public int getCount() {
		return count;

	}


	public BinaryTreeNode getRoot() {
		return root;
	}


	/**
	 * Finds the next node in the tree
	 * 
	 * @param node
	 *            (is not modified)
	 * @param rightParents
	 *            (is not modified) A stack that contains all the nodes that are (1) above the current node and (2) to
	 *            the right.
	 * @return The item in the tree immediately subsequent to the given node. If no node is found, then null is
	 *         returned.
	 */
	private BinaryTreeNode findNext(final BinaryTreeNode node, final Stack<BinaryTreeNode> rightParents) {
		BinaryTreeNode cur = node;
		// if this node is not a leaf, and has a right child, go down to the right child, then left until there are no
		// more.
		if (cur.right != null) {
			cur = cur.right;
			while (cur.left != null) {
				cur = cur.left;
			}
			return cur;
		}

		// If this has no right child, then go to right parent.
		if (!rightParents.isEmpty()) { //
			return rightParents.peek();
		}

		// If there is no node to the right, then return null.
		return null;
	}


	/**
	 * Finds the next node in the tree
	 * 
	 * @param node
	 *            (is not modified)
	 * @param leftParents
	 *            (is not modified) A stack that contains all the nodes that are (1) above the current node and (2) to
	 *            the right.
	 * @return The item in the tree imediately prior to the given node. If no node is found, then null is returned.
	 */
	private BinaryTreeNode findPrevious(final BinaryTreeNode node, final Stack<BinaryTreeNode> leftParents) {
		BinaryTreeNode cur = node;
		// if this node is not a leaf, and has a right child, go down to the right child, then left until there are no
		// more.
		if (cur.left != null) {
			// go one to the left
			cur = cur.left;

			// go right as far as possible
			while (cur.right != null) {
				cur = cur.right;
			}
			return cur;
		}

		// If this has no right child, then go to left parent.
		if (!leftParents.isEmpty()) { //
			return leftParents.peek();
		}

		// If there is no node to the left, then return null.
		return null;
	}


	private static BinaryTreeNode getRightMost(BinaryTreeNode node) {
		if (node.right == null) {
			return node;
		} else {
			return getRightMost(node.right);
		}
	}


	private static BinaryTreeNode getLeftMost(BinaryTreeNode node) {
		if (node.left == null) {
			return node;
		} else {
			return getLeftMost(node.left);
		}
	}


	private static void balance(BinaryTreeNode root) {

	}


	/**
	 * Changes the structure from From: To: A B \ / \ B A * \ *
	 * 
	 * @param A
	 *            in above chart
	 * @return B in above chart
	 */
	private static BinaryTreeNode rotateLeft(BinaryTreeNode root) {
		if (root.right.left != null)
			throw new RuntimeException("Not a valid config");

		root.right.left = root; // B.left = A
		root = root.right; // Root = B
		root.right.right = null; // A.right = null
		return root;
	}


	/**
	 * Changes the structure from From: To: C B / / \ B * C / *
	 * 
	 * @param C
	 *            in above chart
	 * @return B in above chart
	 */
	private static BinaryTreeNode rotateRight(BinaryTreeNode root) {
		if (root.left.right != null)
			throw new RuntimeException("Not a valid config");


		// b becomes the new root.
		// c takes ownership of b's right child, as its left child. In this case, that value is null.
		// b takes ownership of c, as it's right child.

		root.left.right = root; // B.right = C
		root = root.left; // Root = B
		root.right.left = null; // C.left = null
		return root;
	}


	/**
	 * Changes the structure from From: To: A B \ / \ C A C / \ \ B * *
	 * 
	 * @param C
	 *            in above chart
	 * @return B in above chart
	 */
	private static BinaryTreeNode rotateDoubleLeft(BinaryTreeNode root) {
		// if (root.right.left != null)
		throw new RuntimeException("Not a valid config");


		// rotate
	}


	private static int balanceFactor(BinaryTreeNode node) {
		return -height(node.left) + height(node.right);
	}


	private static int height(BinaryTreeNode root) {
		if (root == null)
			return 0;

		return Math.max(height(root.left), height(root.right)) + 1;
	}


	private static boolean isLeaf(BinaryTreeNode node) {
		if (node == null)
			return false;
		return (node.left == null && node.right == null);
	}


}