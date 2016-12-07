package fullboard;

import java.util.*;

class Map {
	private final static char WALL = '\u2593';

	private final static char SPACE = ' ';

	private final static int MAX_SIZE = 60;

	private final int rows;

	private final int cols;

	private final char[][] grid; // [row][column]

	// The number of non-wall spaces.
	// Used to terminate the node tree when all the spaces are filled.
	private int spacesCount = 0;

	private ArrayList<Solution> solutions = new ArrayList<>();


	/**
	 * A two dimensional char array, where each element is either a wall or a space.
	 * 
	 * @param lines
	 *            A sublist of the complete list of lines in the provided file that only includes a single map without
	 *            the header or footer
	 * 
	 */
	public Map(List<String> lines) {

		rows = lines.size();
		cols = lines.get(0).length();// Working under the assumption that all lines are the same length.
		if (rows > MAX_SIZE || cols > MAX_SIZE)
			throw new IllegalArgumentException("Grid is too large! Must be no more than 60 X 60.");
		grid = new char[rows][cols];

		for (int row = 0; row < rows; row++) {
			String line = lines.get(row);
			for (int col = 0; col < cols; col++) {
				setGridValueFromLine(row, line, col);
			}
		}
		findSolutions();
	}


	private void setGridValueFromLine(int row, String line, int col) {
		if (line.length() != cols)
			throw new RuntimeException(
					"The length of this row was " + line.length() + ", but cols = " + cols + " in the line: \n" + line);

		char c = line.charAt(col);
		if (!isValidChar(c))
			throw new IllegalArgumentException("An illegal character, '" + c + "' was contained in the map definition");
		if (c == SPACE)
			spacesCount++;
		grid[row][col] = c;
	}


	public char getChar(int row, int col) {
		return grid[row][col];
	}


	public boolean isSpace(int row, int col) {
		if (row < 0 || row >= rows || col < 0 || col >= cols)
			return false;
		return getChar(row, col) == SPACE;
	}


	/**
	 * Prints the entire map.
	 */
	public final void print() {
		for (char[] row : this.grid) {
			for (char c : row) {
				System.out.print(c);
			}
			System.out.println();
		}
	}


	/**
	 * Checks is a given character is one of the allowed characters.
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isValidChar(char c) {
		switch (c) {
		case WALL:
		case SPACE:
			return true;
		}
		return false;
	}


	public int getSpacesCount() {
		return spacesCount;
	}


	public void findSolutions() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (isSpace(i, j)) {
					new Node(this, i, j);
				}
			}
		}
	}


	public void addSolution(Solution s) {
		solutions.add(s);
	}


	public void printSolutions() {
		Collections.sort(solutions);

		System.out.println("map");
		if (solutions.isEmpty()) {
			System.out.println("No solution");
			this.print();
		} else {
			for (Solution s : solutions) {
				s.print();
			}
		}
		System.out.println("endmap");
	}


	public char[][] getDeepCopyOfGrid() {
		char[][] result = grid.clone();
		for (int i = 0; i < result.length; i++) {
			result[i] = result[i].clone();
		}
		return result;
	}
}