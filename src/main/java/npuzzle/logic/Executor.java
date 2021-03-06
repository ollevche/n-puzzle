package npuzzle.logic;

import npuzzle.io.Output;

import java.util.*;

import static npuzzle.utils.Constants.*;

class Executor {

	@FunctionalInterface
	public interface Algorithm {
		/**
		 * @param initial - the Starting/Initial State from input
		 * @return all parents chained together -> form a list of all moves made to achieve the final state
		 */
		Output execute(State initial);
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
	private static Output executeGreedy(State initial) {
		int maxNumberOfStates = 0;
		Set<State> closedSet = new HashSet<>();
		Set<State> children;
		State current = initial;

		while (current.isNotFinal()) {
			closedSet.add(current);
			children = current.createChildren();
			children.removeAll(closedSet);
			current = Collections.min(children);
			if (closedSet.size() > maxNumberOfStates)
				maxNumberOfStates = closedSet.size();
		}

		return Output.create(0, maxNumberOfStates, current.collectPath());
	}

	private static Output executeAstar(State initial) {
		int everInOpenSet = 1, maxNumberOfStates = 0, currentNumberOfStates = 0;
		Set<State> closedSet = new HashSet<>();
		Set<State> openSet = new HashSet<>();
		Set<State> children;
		State current = initial;

		while (current.isNotFinal()) {
			closedSet.add(current);
			openSet.remove(current);
			children = current.createChildren();
			children.removeAll(closedSet);
			openSet.addAll(children);
			everInOpenSet += children.size();
			if (openSet.isEmpty()) break;
			current = Collections.min(openSet);
			currentNumberOfStates = openSet.size() + closedSet.size();
			if (currentNumberOfStates > maxNumberOfStates)
				maxNumberOfStates = currentNumberOfStates;
			if ( openSet.size() >= 2000 ) {
				List<State> t = new ArrayList<>(openSet);
				t.sort(Comparator.naturalOrder());
				openSet = new HashSet<>(t.subList(0, 1000));
			}
		}

		return Output.create(everInOpenSet, maxNumberOfStates, current.collectPath());
	}

	private static Output executeUniform(State initial) {
		int everInOpenSet = 1, maxNumberOfStates = 0, currentNumberOfStates = 0;
		Set<State> closedSet = new HashSet<>();
		Set<State> openSet = new HashSet<>();
		Set<State> children;
		State current = initial;

		while (current.isNotFinal()) {
			closedSet.add(current);
			openSet.remove(current);
			children = current.createChildren();
			children.removeAll(closedSet);
			openSet.addAll(children);
			everInOpenSet += children.size();
			if (openSet.isEmpty()) break;
			current = Collections.min(openSet);
			currentNumberOfStates = openSet.size() + closedSet.size();
			if (currentNumberOfStates > maxNumberOfStates)
				maxNumberOfStates = currentNumberOfStates;
		}

		return Output.create(everInOpenSet, maxNumberOfStates, current.collectPath());
	}

	static Algorithm getAlgorithm(String algorithm) {
		switch (algorithm) {
			case ASTAR : return Executor::executeAstar;
			case GREEDY : return Executor::executeGreedy;
			case UNIFORM : return Executor::executeUniform;
			default : return null;
		}
	}

}
