package npuzzle.logic;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static npuzzle.utils.Constants.*;

class Evaluator {

	private static final Map<Integer, List<Pair<Integer, Integer>>> xyListMap = new HashMap<>();
	static {
		addReferenceList(3);
		addReferenceList(4);
		addReferenceList(5);
	}

	@FunctionalInterface
	public interface Heuristic {
		int evaluate(State state, int n);
	}

	private static int manhattan(State state, int n) {
		List<Pair<Integer, Integer>> xyList = xyListMap.get(n);
		List<? extends Integer> tiles = state.getTiles();
		Integer tile;
		int x, y;
		int stateEval = 0;

		for (int index = 0; index < tiles.size(); index++) {
			x = index % n;
			y = index / n;
			tile = tiles.get(index);
			stateEval += Math.abs(x - xyList.get(tile).getKey()) + Math.abs(y - xyList.get(tile).getValue());
		}
		return stateEval;
	}

	private static int euclidean(State state, int n) {
		List<Pair<Integer, Integer>> xyList = xyListMap.get(n);
		List<? extends Integer> tiles = state.getTiles();
		Integer tile;
		int x, y;
		int stateEval = 0;

		for (int index = 0; index < tiles.size(); index++) {
			x = index % n;
			y = index / n;
			tile = tiles.get(index);
			stateEval += Math.sqrt(Math.pow(x - xyList.get(tile).getKey(), 2) + Math.pow(y - xyList.get(tile).getValue(), 2));
		}
		return stateEval;
	}

//	Counts how many tiles are not in the correct place
	private static int hamming(State state, int n) {

		return 0;
	}

	static Heuristic getHeuristic(String heuristic) {
		switch (heuristic) {
			case MANHATTAN:
				return Evaluator::manhattan;
			case HAMMING:
				return Evaluator::hamming;
			case EUCLIDEAN:
				return Evaluator::euclidean;
			default:
				return null;
		}
	}

	static void addReferenceList(int n) {
		if (xyListMap.containsKey(n))
			return;
		List<Pair<Integer, Integer>> xyList = new ArrayList<>();
		int nByN = n * n;
		int x = 0, y = 0, i = 0;

		while (i < nByN) {
			while (x < n) {
				xyList.add(new ImmutablePair<>(x, y));
				x++;
			}
			i += x;
			x = 0;
			y++;
		}
		xyListMap.put(n, Collections.unmodifiableList(xyList));
	}

}
