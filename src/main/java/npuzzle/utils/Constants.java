package npuzzle.utils;

public class Constants {

	private Constants() {
		throw new AssertionError();
	}

	/**
	 * should be used everywhere, where it refers to an empty tile in state tiles
	 * added to easily migrate when changing empty tile to 9
	 */
	public static final int EMPTY = 0;

//	algorithms
	public static final String GREEDY = "greedy";
	public static final String UNIFORM = "uniform";
	public static final String ASTAR = "astar";

//	heuristics
	public static final String MANHATTAN = "manhattan";
	public static final String HAMMING = "hamming";
	public static final String EUCLIDEAN = "euclidean";

//	options
	public static final String ALGORITHM = "algorithm";
	public static final String HEURISTIC = "heuristic";
	public static final String FILE = "file";
	public static final String RANDOM = "random";

	// option descriptions
	public static final String ALGORITHM_DESCRIPTION = "Algorithm to use.";
	public static final String HEURISTIC_DESCRIPTION = "Heuristic function to use.";
	public static final String FILE_DESCRIPTION = "File to use as input.";
	public static final String RANDOM_DESCRIPTION = "Use random input.";

}
