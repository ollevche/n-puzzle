package npuzzle.utils;

public enum Error {

	NOT_ENOUGH_TILES("Invalid input: not enough tiles. Diff: "),
	NO_SIZE("Invalid Input: size not provided"),
	OVER_MAX("Invalid Input: Tile has value over max allowed"),
	DUPLICATES("Invalid Input: cannot contain duplicate values"),
	WRONG_AMOUNT("Invalid Input: wrong number of tiles. Missing: "),
	NON_NUMERIC("Invalid Input: cannot contain non-numerals. String: "),
	RANDOM_TOO_SMALL("Invalid Input: cannot generate puzzle with n = "),
	ARG_NOT_FOUND("Invalid input: argument not found: "),
	UNSOLVABLE("Invalid input: unsolvable puzzle"),
	INCOMPATIBLE_HEURISTIC("Invalid input: incompatible heuristic");

    private final String errorMsg;

	Error(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return errorMsg;
	}

}
