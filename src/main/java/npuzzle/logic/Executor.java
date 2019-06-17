package npuzzle.logic;

import npuzzle.utils.Constants;

import java.util.Collections;
import java.util.List;

public class Executor {

	@FunctionalInterface
	public interface Algorithm {
		List<State> execute(State startingState);
	}

	public static List<State> executeGreedy(State startingState) {
		return Collections.emptyList();
	}

	public static List<State> executeAstar(State startingState) {
		return Collections.emptyList();
	}

	public static List<State> executeUniform(State startingState) {
		return Collections.emptyList();
	}

	public static Algorithm getAlgorithm(String algorithm) {
		switch (algorithm) {
			case Constants.ASTAR:
				return Executor::executeAstar;
			case Constants.GREEDY:
				return Executor::executeGreedy;
			case Constants.UNIFORM:
				return Executor::executeUniform;
			default:
				return null;
		}
	}

}
