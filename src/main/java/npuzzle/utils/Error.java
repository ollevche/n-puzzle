package npuzzle.utils;

public enum Error {
	NOT_ENOUGH_TILES("Invalid input: not enough Tiles. Missing: "),
	EMPTY("Invalid input: cannot contain empty lines"),
	NO_SIZE("Invalid Input: size not provided"),
	OVER_MAX("Invalid Input: Tile has value over max allowed"),
	DUPLICATES("Invalid Input: cannot contain duplicate values"),
	WRONG_AMOUNT("Invalid Input: wrong number of tiles. Missing: "),
	NON_NUMERIC("Invalid Input: cannot contain non-numerals. String: ");

	private final String errorMsg;

	Error(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
