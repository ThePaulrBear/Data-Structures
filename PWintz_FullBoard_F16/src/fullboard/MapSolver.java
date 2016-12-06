package fullboard;

public class MapSolver {
	private static final String START_TAG = "solution";

	private static final String END_TAG = "endsolution";

	private static final char START = 'S';

	private static final char FINISH = 'F';


	private Map map;


	public MapSolver(Map map) {
		this.map = map;
	}


	public int minMoves() {
		return -1;
	}


	public String result() {
		return ("Not implemented");
	}


	private class Solution {

		char[][] map;


		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (char[] row : map) {
				for (char c : row) {
					sb.append(c);
				}
				sb.append('\n');
			}
			return sb.toString();
		}
	}
}
