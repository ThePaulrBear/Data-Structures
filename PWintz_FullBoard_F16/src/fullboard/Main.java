package fullboard;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;

public class Main {
	private static final String MAP_START = "map";

	private static final String MAP_END = "endmap";

	private static final String FILE_NOT_FOUND = "File not found.\nComplete\n";


	public static void main(String... args) {
		if (args == null || args.length != 1) {
			System.out.print(FILE_NOT_FOUND);
			return;
		}

		String filename = args[0];
		System.out.println(filename);

		List<String> lines;
		try {
			lines = FileUtils.readLines(new File(filename));
		} catch (IOException e) {
			System.out.print(FILE_NOT_FOUND);
			return;
		}

		int start = -1;
		int end = -1;
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.equals(MAP_START)) {
				start = ++i;
			} else if (line.equals(MAP_END)) {
				end = i;
			}

			if (start != -1 && end != -1) {
				List<String> subList = lines.subList(start, end);
				Map map = new Map(subList);
				map.printSolutions();
				start = -1;
				end = -1;
			}
		}
	}
}
