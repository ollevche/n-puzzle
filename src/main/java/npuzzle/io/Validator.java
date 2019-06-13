package npuzzle.io;

import npuzzle.utils.Error;
import npuzzle.utils.InvalidInputException;
import npuzzle.utils.Utils;

import java.util.*;
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
			throw new InvalidInputException(Error.EMPTY);

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
			throw new InvalidInputException(Error.DUPLICATES);
	}

	private void checkMaxSizeAndValue(List<Integer> intValues) {
		if (intValues.stream().anyMatch(i -> i > (Utils.getN() * Utils.getN()) - 1))
			throw new InvalidInputException(Error.OVER_MAX);

		if (intValues.size() != Utils.getN())
			throw new InvalidInputException(Error.WRONG_AMOUNT);
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
				throw new InvalidInputException(Error.NO_SIZE);
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
				throw new InvalidInputException(Error.NON_NUMERIC, s);
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

	void checkEnoughTiles() {
		int diff = (int) Math.pow(Utils.getN(), 2) - tiles.size();

		if (tiles.size() != Math.pow(Utils.getN(), 2))
			throw new InvalidInputException(Error.NOT_ENOUGH_TILES, String.valueOf(diff));
	}
}
