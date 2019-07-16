package npuzzle.logic;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static npuzzle.utils.Constants.MANHATTAN;

// TODO: add 2 more heuristic functions
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
		List<Integer> tiles = state.getTiles();
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

	static Heuristic getHeuristic(String heuristic) {
		switch (heuristic) {
			case MANHATTAN:
				return Evaluator::manhattan;
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
