package fullboard;

import java.util.*;

class Map {
	private final static char WALL = '\u2593';

	private final static char SPACE = ' ';

	private final static int MAX_SIZE = 60;

	private final int rows;

	private final int cols;

	private final char[][] grid; // [row][column]


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
			throw new IllegalArgumentException("Grid is too large! Must be smaller than 60 X 60.");
		grid = new char[rows][cols];

		for (int row = 0; row < rows; row++) {
			String line = lines.get(row);
			for (int col = 0; col < cols; col++) {
				if (line.length() != cols)
					throw new RuntimeException("The length of this row was " + line.length() + ", but cols = " + cols
							+ " in the line: \n" + line);

				char c = line.charAt(col);
				if (!isValidChar(c))
					throw new IllegalArgumentException(
							"An illegal character, '" + c + "' was contained in the map definition");
				grid[row][col] = c;
			}
		}
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
		System.out.println();
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


	private static void printSampler() {
		System.out.println(String.valueOf(WALL) + String.valueOf(SPACE) + String.valueOf(WALL));
	}
}