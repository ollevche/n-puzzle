package npuzzle.logic;

import npuzzle.utils.Constants;

//	TODO: these modes should be replaced by 3 heuristic functions. Constants should determine steps higher
public class Evaluator {

	private static int manhattan(State state) {
		return 0;
	}

	private static int greedy(State state) {
		return 0;
	}

	private static int uniform(State state) {
		return 0;
	}

	@FunctionalInterface
	public interface Heuristic {

		int evaluate(State state);

	}

	public static Heuristic getHeuristic(String s) {
		switch (s) {
			case Constants.MANHATTAN:
				return Evaluator::manhattan;
			case Constants.GREEDY:
				return Evaluator::greedy;
			case Constants.UNIFORM:
				return Evaluator::uniform;
			default:
				return null;
		}
	}

}
