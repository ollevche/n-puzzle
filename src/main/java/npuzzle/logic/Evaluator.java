package npuzzle.logic;

import npuzzle.utils.Constants;

//	TODO: these modes should be replaced by 3 heuristic functions. Constants should determine steps higher
public class Evaluator {

	public static int evaluateManhattan(State state) {
		return state.getTiles().size();
	}

	public static int evaluateGreedy(State state) {
		return 0;
	}

	public static int evaluateUniform(State state) {
		return 0;
	}

	@FunctionalInterface
	public interface EvaluateInterface{
		int evaluate(State state);
	}

	public static EvaluateInterface getInterface(String s) {
		switch (s) {
			case Constants.MANHATTAN :
				return Evaluator::evaluateManhattan;
			case Constants.GREEDY :
				return Evaluator::evaluateGreedy;
			case Constants.UNIFORM :
				return Evaluator::evaluateUniform;
		}
		return null;
	}

}
