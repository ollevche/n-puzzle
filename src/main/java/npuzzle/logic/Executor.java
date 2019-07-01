package npuzzle.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import npuzzle.utils.Constants;

public class Executor {

	@FunctionalInterface
	public interface Algorithm {
		/**
		 *
		 * @param initial - the Starting/Initial State from input
		 * @return all parents chained together -> form a list of all moves made to achieve the final state
		 */
		List<State> execute(State initial);
	}

	/**
	 * Flow:
	 * 1. create closed set
	 * 2. current = initial
	 * 3. while current != final
	 * 4. add current to closed set
	 * 5. get StateMap with possible moves state.createChildren()
	 * 6. remove children which are in closed set
	 * 7. if no children left -> break
	 * 8. current = best child
	 *
	 * @see Algorithm#execute(State)
	 */
	private static List<State> executeGreedy(State initial) {
		List<State> closedSet = new ArrayList<>();
		State current = initial;
		List<State> children;

		while (!current.isFinal()) {
			closedSet.add(current);
			children = current.createChildren();
			if (children.isEmpty()) {
				System.out.println("unsuccessful"); // TODO: DEL
				break;
			}
			Collections.sort(children);
			current = children.get(0);
		}

		return current.collectPath();
	}

//	TODO: add g(x)
//	TODO: test
	private static List<State> executeAstar(State initial) {
		List<State> closedSet = new ArrayList<>();
		List<State> openSet = new ArrayList<>();
		State current = initial;
		List<State> children;

		while (!current.isFinal()) {
			closedSet.add(current);
			openSet.remove(current);
			children = current.createChildren();
			children.removeAll(closedSet);
			openSet.addAll(children);
			if (openSet.isEmpty())
				break;
			Collections.sort(openSet);
			current = openSet.get(0);
		}

		return current.collectPath();
	}

	private static List<State> executeUniform(State initial) {
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
