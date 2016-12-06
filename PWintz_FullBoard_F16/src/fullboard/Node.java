package fullboard;

import static fullboard.Direction.*;

import java.util.*;

/**
 * This class will take a map and a starting location, and return a set of all the possible solutions starting from that
 * location.
 * 
 * @author PaulWintz
 *
 */
public class Node {
	private final Map map;

	private final int row;

	private final int col;

	private final Node parent;

	private final Node left;

	private final Node up;

	private final Node right;

	private final Node down;

	private final Direction directionFromParent;

	private final int depth;


	// Root node constructor
	public Node(Map map, int row, int col) {
		this.parent = null;
		this.map = map;
		this.row = row;
		this.col = col;
		this.depth = 0;

		this.left = createChild(LEFT);
		this.up = createChild(UP);
		this.right = createChild(RIGHT);
		this.down = createChild(DOWN);

		this.directionFromParent = null;
	}


	Node(Node parent, Direction directionFromParent) {
		if (parent == null || directionFromParent == null) {
			throw new NullPointerException("Programming error: null values should not be used.");
		}
		this.parent = parent;
		this.map = parent.map;
		this.row = parent.row + directionFromParent.deltaRow();
		this.col = parent.col + directionFromParent.deltaColumn();
		this.directionFromParent = directionFromParent;
		this.depth = parent.depth + 1;

		if (this.depth == map.getSpacesCount()) {
			printRoute();

			Solution sol = getSolution();
			String solution = map.overlaySolution(map, sol);
			System.out.print(solution);
			throw new RuntimeException("Temp exception: Stopped for debugging");
		}

		// TODO: Right now, the the token does not continue in the
		// same direction, and can turn at each space.
		this.left = createChild(LEFT);
		this.up = createChild(UP);
		this.right = createChild(RIGHT);
		this.down = createChild(DOWN);
	}


	private Node createChild(Direction dir) {
		if (isSpaceOpen(dir)) {
			return new Node(this, dir);
		} else {
			return null;
		}
	}


	/**
	 * Checks if the adjacent space in the specified direction is empty.
	 * 
	 * @param direction
	 * @return true, if the specified space is open
	 */
	private boolean isSpaceOpen(Direction dir) {
		return isSpaceOpen(this.row + dir.deltaRow(), this.col + dir.deltaColumn());
	}


	/**
	 * Checks the given space to see if there is not a wall there, and then checks if the route taken to get to the
	 * current node already visited that spot.
	 * 
	 * @param row
	 * @param col
	 * @return true, if the specified space is open
	 */
	private boolean isSpaceOpen(int row, int col) {
		// check that the specified location is no a wall or out of bounds
		if (!map.isSpace(row, col))
			return false;

		// Iterate through all the ancestors of this node to verify that they are not in the space being checked.
		Node cur = this;
		while (cur != null) {
			if (cur.row == row && cur.col == col)
				return false;
			cur = cur.parent;
		}

		return true;
	}


	private Node getRoot() {
		Node cur = this;
		while (cur.parent != null) {
			cur = cur.parent;
		}
		return cur;
	}


	private Solution getSolution() {
		return new Solution(map, getRoot(), getRoute());
	}


	/**
	 * Finds the route to the current node from the start.
	 * 
	 * @return
	 */
	public ArrayList<Direction> getRoute() {
		ArrayList<Direction> route = new ArrayList<>();
		Node cur = this;

		while (cur.parent != null) {
			route.add(cur.directionFromParent);
			cur = cur.parent;
		}
		Collections.reverse(route);
		return route;
	}


	public void printRoute() {
		for (Direction dir : getRoute()) {
			char arrow = dir.arrow;
			System.out.print(arrow);
		}
		System.out.println();
	}


	public int getRow() {
		return row;
	}


	public int getColumn() {
		return col;
	}
}