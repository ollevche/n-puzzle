package npuzzle.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dpozinen
 * @author ollevche
 * 
 *         used to read program input
 * 
 */

public class Reader {

	private String fullFileName;

	public Reader() {
	}

	public Reader(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	public List<String> read(boolean fromFile) {
		try {
			List<String> linesList;
			if (fullFileName != null) {
				linesList = readFromFile();
				validateList(linesList);
			}
		} catch (IOException | RuntimeException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	private void validateList(List<String> linesList) throws RuntimeException {
		for (String s : linesList)
			validateLine(s);
	}
	
	private void validateLine(String s) {
		if (s.isEmpty())
			throw new RuntimeException("Invaild Input");
	}

	private List<String> readFromFile() throws IOException {
		return Files.readAllLines(Paths.get(fullFileName), StandardCharsets.UTF_8);
	}

}