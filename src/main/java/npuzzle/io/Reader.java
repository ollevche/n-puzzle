package npuzzle.io;


import npuzzle.logic.State;

import java.io.*;
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
	private List<Integer> tiles = new ArrayList<>();

	public Reader(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	public State read() {
		try {
			readInput();
			return createStartingState();
		} catch (IOException e) {
			System.err.println(e.getMessage());
//			e.printStackTrace();
		}
		return null;
	}

	private void readInput() throws IOException {
		String line;
		Validator validator = new Validator(tiles);
		InputStream inputStream;

		if (fullFileName != null)
			inputStream = new BufferedInputStream(new FileInputStream(fullFileName));
		else
			inputStream = System.in;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			while ((line = br.readLine()) != null)
				validator.validateLine(line);
		}
		validator.checkEnoughTiles();
	}

	@Deprecated
	private List<String> readFromFile() throws IOException {
		return Files.readAllLines(Paths.get(fullFileName), StandardCharsets.UTF_8);
	}

	private State createStartingState() {
		return new State(tiles);
	}
}
