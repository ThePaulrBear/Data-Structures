package spellchecker;

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
		for (int i = 0; i < lines.size(); i++) {
			binaryWordTree.add(lines.get(i).trim());
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
		if (binaryWordTree.find(word) != null)
			return null;
		else
			return new String[1];
	}


	/**
	 * Adds the given word to the dictionary's binary tree. Duplicates are ignored.
	 * 
	 * @param word
	 */
	public void add(String word) {
		binaryWordTree.add(word);
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

}
