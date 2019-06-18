package npuzzle.utils;

// TODO: replace/remove this class

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

	private Utils() {
	}

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

	public static List<Integer> generateRandom(int n) {
		List<Integer> tiles = new ArrayList<>(n);
		while (--n >= 0)
			tiles.add(n);
		Collections.shuffle(tiles);
		return tiles;
	}

}
