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

	private final Direction directionFromParent;

	private final int depth;

	private int moveNumber;


	// Root node constructor
	public Node(Map map, int row, int col) {
		this.parent = null;
		this.map = map;
		this.row = row;
		this.col = col;
		this.depth = 1;
		this.moveNumber = 1;
		this.directionFromParent = null;

		createChild(LEFT);
		createChild(UP);
		createChild(RIGHT);
		createChild(DOWN);

	}


	Node(Node parent, Direction directionFromParent) throws StackOverflowError {
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
			this.moveNumber = parent.moveNumber;
			map.addSolution(getSolution());
			return;
		}

		try {
			if (isSpaceOpen(directionFromParent)) {
				this.moveNumber = parent.moveNumber;
				createChild(directionFromParent);
			} else {
				this.moveNumber = parent.moveNumber + 1;

				createChild(LEFT);
				createChild(UP);
				createChild(RIGHT);
				createChild(DOWN);
			}
		} catch (StackOverflowError e) {

			this.printRoute();
		}
	}


	private boolean createChild(Direction dir) throws StackOverflowError {
		if (isSpaceOpen(dir)) {
			new Node(this, dir);
			return true;
		} else {
			return false;
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


	/**
	 * Finds the ancestor node that has no parent.
	 * 
	 * @return
	 */
	private Node getRoot() {
		Node cur = this;
		while (cur.parent != null) {
			cur = cur.parent;
		}
		return cur;
	}


	private Solution getSolution() {
		return new Solution(map, getRoot(), getRoute(), moveNumber);
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