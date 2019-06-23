package npuzzle.logic;

import npuzzle.io.Input;
import npuzzle.utils.Constants;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: add 2 more heuristic functions

public class Evaluator {

	@FunctionalInterface
	public interface Heuristic {
		int evaluate(State state);
	}

	private static int manhattan(State state) {

		// TODO: fix: xyList cannot be created on load but shouldn't be calculated every time
		List<Pair<Integer, Integer>> xyList = createReferenceList();

		List<Integer> tiles = state.getTiles();
		Integer tile;
		int x, y, n = Input.getInstance().getN();
		int stateEval = 0;

		for (int index = 0; index < tiles.size(); index++) {
			x = index % n;
			y = index / n;
			tile = tiles.get(index);
			stateEval += Math.abs(x - xyList.get(tile).getKey()) + Math.abs(y - xyList.get(tile).getValue());
		}
		return stateEval;
	}

	public static Heuristic getHeuristic(String heuristic) {
		switch (heuristic) {
			case Constants.MANHATTAN:
				return Evaluator::manhattan;
			default:
				return null;
		}
	}

	private static List<Pair<Integer, Integer>> createReferenceList() {
		List<Pair<Integer, Integer>> xyList = new ArrayList<>();
		int n = Input.getInstance().getN(), nByN = n * n;
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
