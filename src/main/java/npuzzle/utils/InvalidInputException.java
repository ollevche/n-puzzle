package npuzzle.utils;

public class InvalidInputException extends RuntimeException {

	public InvalidInputException(Error err) {
		super(err.toString());
	}

	public InvalidInputException(Error err, String s) {
		super(err + " " + s);
	}

}

