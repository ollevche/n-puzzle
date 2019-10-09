package npuzzle.logic;

import static npuzzle.utils.Constants.*;

import java.util.*;

class Evaluator {

	private static final Map<Integer, List<Point>> xyListMap = new HashMap<>();
    private static final Map<Integer, State> finalStateMap = new HashMap<>();

	@FunctionalInterface
	public interface Heuristic {
		int evaluate(State state, int n);
	}

	private static int manhattan(State state, int n) {
		List<Point> xyList = xyListMap.get(n);
		List<Integer> tiles = state.getTiles();
		Point correctTile;
		int x, y; int stateEval = 0;

		for (int index = 0; index < tiles.size(); index++) {
			x = index / n;
			y = index % n;
			correctTile = xyList.get(tiles.get(index));
			stateEval += Math.abs(x - correctTile.x) + Math.abs(y - correctTile.y);
		}
		return stateEval;
	}

	private static int euclidean(State state, int n) {
		List<Point> xyList = xyListMap.get(n);
		List<Integer> tiles = state.getTiles();
		Point correctTile;
		int x, y; int stateEval = 0;

		for (int index = 0; index < tiles.size(); index++) {
			x = index / n;
			y = index % n;
			correctTile = xyList.get(tiles.get(index));
			stateEval += Math.pow(x - correctTile.x, 2) + Math.pow(y - correctTile.y, 2);
		}
		return stateEval;
	}

//	Counts how many tiles are not in the correct place
	private static int hamming(State state, int n) {
		List<Integer> target = finalStateMap.get(n).getTiles();
		int diff = 0;

		for (int i = 0; i < state.getTiles().size(); i++)
			if (!state.getTiles().get(i).equals(target.get(i)))
				diff++;
		return diff;
	}

	static Heuristic getHeuristic(String heuristic) {
		switch (heuristic) {
			case MANHATTAN: return Evaluator::manhattan;
			case HAMMING : return Evaluator::hamming;
			case EUCLIDEAN : return Evaluator::euclidean;
			default : return null;
		}
	}

	static void addReferenceList(int n) {
		if (xyListMap.containsKey(n)) return;
		State finalState = State.createFinal(n);
		finalStateMap.put(n, finalState);
		List<Integer> finalOrder = finalState.getTiles();
		List<Point> xyList = new ArrayList<>(Collections.nCopies(finalOrder.size(), null));

		for (int i = 0; i < finalOrder.size(); i++)
			xyList.set(finalOrder.get(i), new Point(i / n, i % n));

		xyListMap.put(n, Collections.unmodifiableList(xyList));
	}

	static State getFinal(int n) {
	    return finalStateMap.get(n);
    }

    private static final class Point {
		private final int x;
		private final int y;
		
		private Point(int x, int y) {
			this.x = x; this.y = y;
		}
	}
    
}
