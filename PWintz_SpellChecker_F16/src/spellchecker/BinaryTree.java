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
			throw new IllegalArgumentException("Words are equal: " + word1 + word2);
		}
	}


	public void add(String word) {
		count++;

		word = word.trim();

		if (root == null) {
			root = new BinaryTreeNode(word);
			return;
		}

		BinaryTreeNode cur = root;

		while (true) {
			if (areWordsInOrder(word, cur.value)) {
				if (cur.left != null) {
					cur = cur.left;
					continue;
				} else {
					cur.left = new BinaryTreeNode(word);
					return;
				}
			} else {
				if (cur.right != null) {
					cur = cur.right;
					continue;
				} else {
					cur.right = new BinaryTreeNode(word);
					return;
				}
			}
		}

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
		word = word.trim();

		BinaryTreeNode cur = root;

		Stack<BinaryTreeNode> stack = new Stack<>();


		while (cur != null) {
			stack.push(cur);

			if (word.equals(cur.value)) {
				// Item is found!
				return null;
			}

			if (areWordsInOrder(word, cur.value)) {
				// word comes lexigraphically before cursor
				cur = cur.left;
			} else {
				// word comes lexigraphically after cursor
				cur = cur.right;
			}
		}

		String[] neighbors = new String[2];

		BinaryTreeNode leftCur = cur;
		while (neighbors[0] != null && neighbors[1] != null) {
			while (leftCur.left == null) {

			}

		}

		return null;
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

		throw new RuntimeException("Programming error. THis line should not be reached.");
	}


	public int getCount() {
		return count;

	}


	public BinaryTreeNode getRoot() {
		return root;
	}
}