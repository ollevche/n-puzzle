package npuzzle.logic;

import npuzzle.utils.Constants;

import java.util.List;

public class Executor {

	@FunctionalInterface
	public interface Algorithm {
		void execute(List<State> states);
	}

	public static void executeGreedy(List<State> states) {

	}

	public static void executeAstar(List<State> states) {

	}

	public static void executeUniform(List<State> states) {

	}

	public Algorithm getAlgorithm(String algorithm) {
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
