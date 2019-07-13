package npuzzle.io;

import npuzzle.utils.Error;
import npuzzle.utils.InvalidInputException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static npuzzle.utils.Constants.*;

// TODO: treat last empty line as EOF
class Validator {

	private final Input input;
	private boolean isNSet;
	private final List<Integer> tiles;
	private int n;

	private Validator(Input input) {
		this.input = input;
		tiles = new ArrayList<>();
	}

	static Validator create(Input input) {
		return new Validator(input);
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
		if (intValues.stream().anyMatch(i -> i > n * n - 1))
			throw new InvalidInputException(Error.OVER_MAX); // TODO: fix 3\n 012 -> 0 1 2

		if (intValues.size() != n)
			throw new InvalidInputException(Error.WRONG_AMOUNT, String.valueOf(n - intValues.size()));
	}

	private boolean trySetN(List<Integer> intValues) {
		if (!isNSet) {
			if (intValues.size() == 1) {
				n = intValues.get(EMPTY);
				return isNSet = true;
			} else
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
		int diff = (int) Math.pow(n, 2) - tiles.size();

		if (diff != 0)
			throw new InvalidInputException(Error.NOT_ENOUGH_TILES, String.valueOf(diff));
	}

	void saveValidatedTiles(Input input) {
		input.setTilesAndN(tiles, n);
	}

	void saveValidRandomArg(String undef) {
		undef = undef.trim();

		if (!undef.matches("\\d+"))
			throw new InvalidInputException(Error.NON_NUMERIC, undef);

		int randomN = Integer.valueOf(undef);
		if (randomN < 2)
			throw new InvalidInputException(Error.RANDOM_TOO_SMALL, undef);

		input.generateRandomTiles(randomN);
	}

	void saveValidAlgorithm(String undef) {
		String algorithm;

		switch (undef.trim().toLowerCase()) {
			case GREEDY:
				algorithm = GREEDY;
				break;
			case UNIFORM:
				algorithm = UNIFORM;
				break;
			case ASTAR:
				algorithm = ASTAR;
				break;
			default:
				throw new InvalidInputException(Error.ARG_NOT_FOUND, undef);
		}

		input.setAlgorithm(algorithm);
	}

	void saveValidHeuristic(String undef) {
		String heuristic;

		switch (undef.trim().toLowerCase()) {
			case MANHATTAN:
				heuristic = MANHATTAN;
				break;
			default:
				throw new InvalidInputException(Error.ARG_NOT_FOUND, undef);
		}

		input.setHeuristic(heuristic);
	}

	void saveValidFile(String absolutePath) {
		input.setFile(absolutePath);
	}

}
