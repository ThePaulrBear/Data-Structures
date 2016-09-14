package ecolicounts;

import static java.lang.System.*;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(in);
		String fileName = scanner.nextLine();
		File file = new File(fileName);

		String fileContents = FileUtils.readFileToString(file);
		int aCount = 0;
		int cCount = 0;
		int gCount = 0;
		int tCount = 0;

		for (char c : fileContents.toCharArray()) {
			switch (c) {
			case 'A':
				aCount++;
				break;
			case 'C':
				cCount++;
				break;
			case 'G':
				gCount++;
				break;
			case 'T':
				tCount++;
				break;
			}
		}

		out.printf("#A = %d\n#C = %d\n#G = %d\n#T = %d\n", aCount, cCount, gCount, tCount);

		scanner.close();
	}
}
