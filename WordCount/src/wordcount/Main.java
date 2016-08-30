package wordcount;

import static java.lang.System.*;
import static org.apache.commons.io.FileUtils.*;

import java.io.*;

public class Main {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String filename = "small_document.txt";
		String text = readFileToString(new File(filename));
		int count = text.split("\\b[\\w|']+\\b").length;
		out.println("There are " + count + " words in " + filename + ".");
	}

}
