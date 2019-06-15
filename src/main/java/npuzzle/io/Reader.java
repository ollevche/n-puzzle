package npuzzle.io;


import npuzzle.logic.State;
import npuzzle.utils.Constants;
import npuzzle.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dpozinen
 * <p>
 * used to read program input
 */

public class Reader {
	private String fullFileName;
	private List<Integer> tiles = new ArrayList<>();

	public Reader() {
	}

	public Reader(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	public State read() {
		try {
			readInput();
			return new State(tiles);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	private void readInput() throws IOException {
		String line;
		InputStream inputStream;
		Validator validator = new Validator(tiles);

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

	public void readArgs(String[] args) {
		List<String> argList = Arrays.asList(args);

		if (argList.isEmpty()) {
			Utils.setMode(Constants.ASTAR);
			return;
		}

		for (String s : argList) {
			if (s.startsWith("file"))
				fullFileName = StringUtils.substringBetween(s, "(", ")");
			if (s.startsWith("mode"))
				setMode(StringUtils.substringBetween(s, "(", ")"));
		}
	}

	private void setMode(String modes) {
		if (StringUtils.containsIgnoreCase(modes, Constants.GREEDY))
			Utils.setMode(Constants.GREEDY);
		else if (StringUtils.containsIgnoreCase(modes, Constants.ASTAR))
			Utils.setMode(Constants.ASTAR);
		else if (StringUtils.containsIgnoreCase(modes, Constants.UNIFORM))
			Utils.setMode(Constants.UNIFORM);
	}

	@Deprecated
	private List<String> readFromFile() throws IOException {
		return Files.readAllLines(Paths.get(fullFileName), StandardCharsets.UTF_8);
	}
}
