package fullboard;

import java.util.*;

public class Solution implements Comparable<Solution> {
	private static final String START_TAG = "solution\n";

	private static final String END_TAG = "endsolution\n";

	private static final char START = 'S';

	private static final char FINISH = 'F';

	public final Map map;

	public final int startRow;

	public final int startCol;

	public final ArrayList<Direction> route;

	public final int moves;


	public Solution(Map map, int startRow, int startCol, ArrayList<Direction> route, int moves) {
		this.map = map;
		this.startRow = startRow;
		this.startCol = startCol;
		this.route = route;
		this.moves = moves;
	}


	public Solution(Map map, Node root, ArrayList<Direction> route, int moves) {
		this(map, root.getRow(), root.getColumn(), route, moves);
	}


	public String toString() {
		char[][] grid = this.map.getDeepCopyOfGrid();

		int row = this.startRow;
		int col = this.startCol;
		for (Direction dir : this.route) {
			grid[row][col] = dir.arrow;
			row += dir.deltaRow();
			col += dir.deltaColumn();
		}
		grid[this.startRow][this.startCol] = START;
		grid[row][col] = FINISH;

		StringBuilder sb = new StringBuilder();
		sb.append(moves + " moves\n");
		sb.append(START_TAG);
		for (char[] rowArray : grid) {
			for (char c : rowArray) {
				sb.append(c);
			}
			sb.append("\n");
		}
		sb.append(END_TAG);
		return sb.toString();
	}


	public void print() {
		System.out.print(this.toString());
	}


	@Override
	public int compareTo(Solution other) {
		return this.toString().compareTo(other.toString());
	}
}