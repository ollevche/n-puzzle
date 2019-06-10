package npuzzle.io;

import npuzzle.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Validator
{
	private boolean isNSet;
	private List<Integer> tiles;

	Validator(List<Integer> tiles){
		this.tiles = tiles;
	}

	public void validateList(List<String> linesList) {
		for (String s : linesList)
			validateLine(s);
	}

	void validateLine(String line) {
		if (line.isEmpty())
			throw new RuntimeException("Invalid Input: empty line");

		List<String> elements = splitLineAndRemoveComments(line);

		if (elements.isEmpty())
			return;

		checkNonNumeric(elements);

		List<Integer> intValues = getIntValues(elements);

		if (trySetN(intValues))
			return;

		checkMaxSizeAndValue(intValues);
		checkDuplicates(intValues);
		checkDuplicates(tiles);
		tiles.addAll(intValues);
	}

	private void checkDuplicates(List<Integer> intValues) {
		if (!intValues.stream().allMatch(new HashSet<Integer>()::add))
			throw new RuntimeException("Duplicate values.");
	}

	private void checkMaxSizeAndValue(List<Integer> intValues) {
		if (intValues.stream().anyMatch(i -> i > (Utils.getN() * Utils.getN()) - 1))
			throw new RuntimeException("Invalid Input: tile has value over max allowed");

		if (intValues.size() != Utils.getN())
			throw new RuntimeException("Invalid Input: wrong number of tiles");
	}

	private boolean trySetN(List<Integer> intValues) {
		if (!isNSet) {
			if ( intValues.size() == 1 )
			{
				Utils.setN(intValues.get(0));
				isNSet = true;
				return true;
			}
			else
				throw new RuntimeException("Invalid Input: Size not provided");
		}
		return false;
	}

	private List<String> splitLineAndRemoveComments(String line) {
		List<String> elements = Arrays.asList(line.split("\\s+"));
		return getPartsBeforeComment(elements);
	}

	private List<Integer> getIntValues(List<String> elements) {
		return elements.stream().map(Integer::valueOf).collect(Collectors.toList());
	}

	private void checkNonNumeric(List<String> elements) {
		elements.forEach(s -> {
			if (!s.matches("\\d+"))
				throw new RuntimeException("Invalid Input for String: <" + s + ">");
		});
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
