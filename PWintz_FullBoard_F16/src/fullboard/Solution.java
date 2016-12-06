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
}