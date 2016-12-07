package fullboard;

import java.util.*;

public class Solution {
	public final Map map;

	public final int startRow;

	public final int startCol;

	public final ArrayList<Direction> route;


	public Solution(Map map, int startRow, int startCol, ArrayList<Direction> route) {
		this.map = map;
		this.startRow = startRow;
		this.startCol = startCol;
		this.route = route;
	}


	public Solution(Map map, Node root, ArrayList<Direction> route) {
		this(map, root.getRow(), root.getColumn(), route);
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
		grid[this.startRow][this.startCol] = 'S';
		grid[row][col] = 'F';

		StringBuilder sb = new StringBuilder();
		for (char[] rowArray : grid) {
			for (char c : rowArray) {
				sb.append(c);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}