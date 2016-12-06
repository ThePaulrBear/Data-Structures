package fullboard;

public enum Direction {
	LEFT(0, '\u2190'), UP(1, '\u2193'), RIGHT(2, '\u2192'), DOWN(3, '\u2191');

	public final int numeral;

	public final char arrow;


	Direction(int numeral, char arrow) {
		this.numeral = numeral;
		this.arrow = arrow;
	}


	int deltaRow() {
		switch (this) {
		case DOWN:
			return -1;
		case UP:
			return 1;
		default:
			return 0;
		}
	}


	int deltaColumn() {
		switch (this) {
		case LEFT:
			return -1;
		case RIGHT:
			return 1;
		default:
			return 0;
		}
	}
}
