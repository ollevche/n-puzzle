package npuzzle.logic;

import static npuzzle.utils.Constants.MANHATTAN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import npuzzle.io.Input;

// TODO: add 2 more heuristic functions

public class Evaluator {

	private static List<Pair<Integer, Integer>> xyList;

	@FunctionalInterface
	public interface Heuristic {
		int evaluate(State state);
	}

	private static int manhattan(State state) {
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
			case MANHATTAN:
				return Evaluator::manhattan;
			default:
				return null;
		}
	}

	public static void createReferenceList() {
		xyList = new ArrayList<>();
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
		xyList = Collections.unmodifiableList(xyList);
	}
}
