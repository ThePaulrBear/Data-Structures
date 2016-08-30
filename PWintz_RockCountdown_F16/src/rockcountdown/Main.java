package rockcountdown;

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
		String[] fileLines = fileContents.split("\r\n");

		ArrayList<Song> songArrayList = new ArrayList<>();

		for (String line : fileLines) {
			songArrayList.add(new Song(line));
		}

		StringBuilder builder = new StringBuilder();

		for (Song song : songArrayList) {
			builder.insert(0, song.getRank() + "\t" + song.getTitle() + "\n");
		}

		out.println(builder.toString());
		out.println("Complete");
	}

}
