package npuzzle.logic;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static npuzzle.utils.Constants.MANHATTAN;

// TODO: add 2 more heuristic functions
class Evaluator {

	@FunctionalInterface
	public interface Heuristic {

		int evaluate(State state, int n, List<Pair<Integer, Integer>> xyGoalList);

	}

	private static int manhattan(State state, int n, List<Pair<Integer, Integer>> xyGoalList) {
		List<Integer> tiles = state.getTiles();
		Integer tile;
		int x, y;
		int stateEval = 0;

		for (int index = 0; index < tiles.size(); index++) {
			x = index % n;
			y = index / n;
			tile = tiles.get(index);
			stateEval += Math.abs(x - xyGoalList.get(tile).getKey()) + Math.abs(y - xyGoalList.get(tile).getValue());
		}
		return stateEval;
	}

	public static Heuristic getHeuristic(String heuristic) {
		switch (heuristic) {
			case MANHATTAN:
				return Evaluator::manhattan;
			default:
				return null;
		}
	}

	static List<Pair<Integer, Integer>> createReferenceList(int n) {
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
		return Collections.unmodifiableList(xyList);
	}

}
