package npuzzle.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dpozinen
 * @author ollevche
 * <p>
 * used to read program input
 */

public class Reader {

	private String fullFileName;
	private List<Integer> tiles;

	public Reader() {
	}

	public Reader(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	public List<String> read() {
		List<String> linesList;
		tiles = new ArrayList<>();

		try {
			if (fullFileName != null) {
				linesList = readFromFile();
				validateList(linesList);
			} else {
				linesList = readFromConsole();
			}
			return linesList;
		} catch (IOException | RuntimeException e) {
//			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private List<String> readFromConsole() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		List<String> lineList = new ArrayList<>();

		while ((line = br.readLine()) != null) {
			validateLine(line);
			lineList.add(line);
		}
		return lineList;
	}

	private List<String> readFromFile() throws IOException {
		return Files.readAllLines(Paths.get(fullFileName), StandardCharsets.UTF_8);
	}

	private void validateList(List<String> linesList) throws RuntimeException {
		for (String s : linesList)
			validateLine(s);
	}

	private void validateLine(String s) {
		if (s.isEmpty())
			throw new RuntimeException("Invalid Input: empty line");

		Arrays.stream(s.split("\\s+")).forEach(e -> {
			if (!e.startsWith("#") && !e.matches("\\d+"))
				throw new RuntimeException("Invalid Input for String: <" + e + ">");
		});

		Predicate<String> predicateIsComment = st -> !st.startsWith("#");
		List<String> elements = Arrays.stream(s.split("\\s+"))
										.filter(predicateIsComment)
										.collect(Collectors.toList());

		for (String element : elements) {
			Integer i = Integer.parseInt(element);
			if (tiles.contains(i))
				throw new RuntimeException("Duplicate values for number: <" + i + ">");
			tiles.add(i);
		}
	}


}