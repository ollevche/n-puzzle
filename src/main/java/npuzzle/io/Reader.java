package npuzzle.io;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author dpozinen
 * @author ollevche
 * <p>
 * used to read program input
 */

public class Reader {

	private String fullFileName;
	private List<Integer> tiles = new ArrayList<>();;
	private Validator validator = new Validator(tiles);

	public Reader() {
	}

	public Reader(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	public List<String> read() {
		List<String> linesList;

		try {
			if (fullFileName != null) {
				linesList = readFromFile();
				validator.validateList(linesList);
			} else {
				linesList = readFromConsole();
			}
			return linesList;
		} catch (IOException e) {
//			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private List<String> readFromConsole() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		List<String> lineList = new ArrayList<>();

		while ((line = br.readLine()) != null) {
			validator.validateLine(line);
			lineList.add(line);
		}
		return lineList;
	}

	private List<String> readFromFile() throws IOException {
		return Files.readAllLines(Paths.get(fullFileName), StandardCharsets.UTF_8);
	}
}
