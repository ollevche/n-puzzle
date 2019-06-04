package npuzzle.io;

import npuzzle.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author dpozinen
 * @author ollevche
 * <p>
 * used to read program input
 */

public class Reader {

	private String fullFileName;
	private List<Integer> tiles;
	private boolean isNSet;

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
			tiles.forEach(System.out::println);
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

	private void validateLine(String line) {
		if (line.isEmpty())
			throw new RuntimeException("Invalid Input: empty line");

		List<String> elements = Arrays.asList(line.split("\\s+"));
		elements = getPartsBeforeComment(elements);

		elements.forEach(s -> {
			if (!s.matches("\\d+"))
				throw new RuntimeException("Invalid Input for String: <" + s + ">");
		});

		List<Integer> intValues = elements.stream().map(Integer::valueOf).collect(Collectors.toList());

		if (!isNSet && intValues.size() == 1) {
			Utils.setN(intValues.get(0));
			isNSet = true;
			return;
		}

		for (Integer i : intValues) {
			if (tiles.contains(i))
				throw new RuntimeException("Duplicate values for number: <" + i + ">");
			tiles.add(i);
		}
	}

	private List<String> getPartsBeforeComment(List<String> elements) {
		List<String> beforeComment = new ArrayList<>();
		boolean isComment = false;

		for (String element : elements) {
			if (element.startsWith("#"))
				isComment = true;
			if (!isComment)
				beforeComment.add(element);
		}
		return beforeComment;
	}

}