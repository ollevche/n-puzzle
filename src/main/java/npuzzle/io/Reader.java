package npuzzle.io;


import npuzzle.logic.State;
import npuzzle.utils.Modes;
import npuzzle.utils.Utils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
			e.printStackTrace(System.err);
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
			Utils.setMode(Modes.ASTAR);
			return;
		}

		argList.replaceAll(String::toLowerCase);
		setMode(argList);
		setFile(argList);
	}

	private void setFile(List<String> argList) {
		for (String s : argList) {
			if (s.startsWith("file"))
				fullFileName = StringUtils.substringBetween(s, "(", ")");
		}
	}

	private void setMode(List<String> argList) {
		if (argList.contains(Modes.GREEDY))
			Utils.setMode(Modes.GREEDY);
		else if (argList.contains(Modes.ASTAR))
			Utils.setMode(Modes.ASTAR);
		else if (argList.contains(Modes.UNIFORM))
			Utils.setMode(Modes.UNIFORM);
	}

	@Deprecated
	private List<String> readFromFile() throws IOException {
		return Files.readAllLines(Paths.get(fullFileName), StandardCharsets.UTF_8);
	}
}
