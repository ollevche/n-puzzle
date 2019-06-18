package npuzzle.logic;

import npuzzle.utils.Constants;
import npuzzle.utils.StateMap;

import java.util.*;

public class Executor {

	@FunctionalInterface
	public interface Algorithm {
		List<State> execute(State startingState);
	}

	private static List<State> executeGreedy(State initial) {

		// 1. create closed set
		// 2. current = starting
		// 3. while current != final
		// 4. add current to closed set
		// 5. get TreeMap<String, State> with possible moves state.createChildren()
		// 6. remove children which are in closed set
		// 7. current = best child

		Set<String> closedSet = new TreeSet<>();
		State current = initial;
		StateMap children;

		while (!current.isFinal()) {
			closedSet.add(current.toString());
			children = current.createChildren();
			children.keySet().removeAll(closedSet);
			current = children.firstEntry().getValue();
		}

		return current.createHierarchy();
	}

	private static List<State> executeAstar(State startingState) {
		return Collections.emptyList();
	}

	private static List<State> executeUniform(State startingState) {
		return Collections.emptyList();
	}

	static Algorithm getAlgorithm(String algorithm) {
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
