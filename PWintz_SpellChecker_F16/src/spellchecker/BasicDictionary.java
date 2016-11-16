package spellchecker;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;

public class BasicDictionary implements Dictionary {

	private BinaryTree binaryWordTree = new BinaryTree();


	/**
	 * Replaces the current dictionary with words imported from the specified text file. Words in the file are in
	 * lexicographical (ASCII) order, one word per line.
	 * 
	 * @param filename
	 *            Name (possibly including a path) of the file to import.
	 * @throws Exception
	 */
	public void importFile(String filename) throws Exception {
		ArrayList<String> lines = (ArrayList<String>) FileUtils.readLines(new File(filename));
		binaryWordTree = new BinaryTree();

		// start at the middle of the array then do the middle of each segment
		double log2 = Math.log(lines.size()) / Math.log(2);
		int sizeRoundedToMultOfTwo = (int) Math.pow(2, Math.ceil(log2));
		for (int n = sizeRoundedToMultOfTwo; n > 1; n /= 2) {
			// System.out.println("start i: " + n / 2 + ", n: " + n);
			for (int i = n / 2; i < lines.size(); i += n) {
				// System.out.println("i: " + i + ", n: " + n);
				binaryWordTree.add(lines.get(i).trim());
			}
		}
		binaryWordTree.add(lines.get(0).trim());

		if (binaryWordTree.getCount() != lines.size())
			throw new RuntimeException(String.format("Sizes do not match. Given: %d, actual: %d", lines.size(),
					binaryWordTree.getCount()));
		else {
			// System.out.println("Lengths match.");
		}
	}


	/**
	 * Loads the specified file from a previously saved dictionary tree file. The file format is text, with one word per
	 * line.
	 * 
	 * @param filename
	 *            Name (possibly including a path) of the file to load from.
	 * @throws Exception
	 */
	public void load(String filename) throws Exception {
		ArrayList<String> lines = (ArrayList<String>) FileUtils.readLines(new File(filename));
		binaryWordTree = new BinaryTree();
		for (int i = 0; i < lines.size(); i++) {
			binaryWordTree.add(lines.get(i).trim());
		}
	}


	/**
	 * Saves the dictionary tree to the specified file in preorder. The file format is text, with one word per line.
	 * 
	 * @param filename
	 *            Name (possibly including a path) of the file to save to. If the file exists, it is overwritten.
	 * @throws Exception
	 */
	public void save(String filename) throws Exception {
		ArrayList<String> list = binaryWordTree.transverseInPreorder();

		FileUtils.writeLines(new File(filename), list);
	}


	/**
	 * 
	 * @param word
	 * @return If the word is <b>found</b> this method returns <b>null</b>. Otherwise, it returns a String array
	 *         organized as follows:
	 * 
	 *         <pre>
	 *         [0] = Preceeding word in the dictionary 
	 *         [1] = Succeeding word in the dictionary 
	 *         
	 *              e.g. if the unknown word was "spelm", the result might be:
	 *              
	 *         [0] = "spells" 
	 *         [1] = "spelt"
	 *         
	 *         If there is no preceeding or succeeding word in the dictionary, set the element to "".
	 *         </pre>
	 */
	public String[] find(String word) {
		return binaryWordTree.find(word);
	}


	/**
	 * Adds the given word to the dictionary's binary tree. Duplicates are ignored.
	 * 
	 * @param word
	 */
	public void add(String word) {
		try {
			binaryWordTree.add(word);
		} catch (IllegalArgumentException e) {
			// a duplicate word is ignored.
			System.out.println(e.getMessage());
		}
	}


	/**
	 * 
	 * @return Returns a reference to the root node of the binary tree.
	 */
	public BinaryTreeNode getRoot() {
		return binaryWordTree.getRoot();
	}


	/**
	 * 
	 * @return Returns the number of words in the dictionary.
	 */
	public int getCount() {
		return binaryWordTree.getCount();
	}


	// REMOVE AT END OF DEVELOPMENT
	public static void testImportFile() throws Exception {
		Dictionary dictionary = new BasicDictionary();

		dictionary.importFile("full_dictionary.txt");

		assertNotNull("Dictionary.getRoot() should not be null.", dictionary.getRoot());

		int depth = getTreeDepth(dictionary);
		int maxDepth = 100;
		if (depth > maxDepth)
			fail("The tree depth is " + depth + " is greater than the maximum allowed depth of " + maxDepth + ".");

		dictionary.save("full_dictionary.pre");
		String s = FileUtils.readFileToString(new File("full_dictionary.pre"));
		String[] parts = s.split("\n");
		assertEquals(175169, parts.length);


	}


	static int treeDepth;


	public static int getTreeDepth(Dictionary dictionary) {
		treeDepth = 0;
		goDeeper(dictionary.getRoot(), 0);
		return treeDepth;
	}


	private static void goDeeper(BinaryTreeNode node, int depth) {
		if (node != null) {
			depth++;
			if (depth > treeDepth)
				treeDepth = depth;

			if (node.left != null)
				goDeeper(node.left, depth);
			if (node.right != null)
				goDeeper(node.right, depth);
		}
	}


	public static void main(String[] args) throws Exception {
		testImportFile();

	}

}
