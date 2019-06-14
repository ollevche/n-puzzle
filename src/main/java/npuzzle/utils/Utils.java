package npuzzle.utils;

public class Utils {
	private Utils(){}

	private static int n;

	private static String mode;

	public static void setN(int setN) {
		n = setN;
	}

	public static int getN() {
		return n;
	}

	public static String getMode() {
		return mode;
	}

	public static void setMode(String mode) {
		Utils.mode = mode;
	}
}
