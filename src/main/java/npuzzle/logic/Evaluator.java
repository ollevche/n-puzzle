package npuzzle.logic;

import npuzzle.utils.Constants;
import npuzzle.utils.Utils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//	TODO: these modes should be replaced by 3 heuristic functions. Constants should determine steps higher
public class Evaluator {

	private static final List<Pair<Integer, Integer>> xyList = createReferenceList();

	@FunctionalInterface
	public interface Heuristic {
		int evaluate(State state);
	}

//	TODO: Test.
	private static int manhattan(State state) {
		List<Integer> tiles = state.getTiles();
		int x, y, n = Utils.getN();
		int stateEval = 0;

		for (Integer i : tiles) {
			x = i % n;
			y = i / n;
			stateEval += Math.abs(x - xyList.get(i).getKey()) + Math.abs(y - xyList.get(i).getValue()) ;
		}
		return stateEval;
	}

	private static int greedy(State state) {
		return 0;
	}

	private static int uniform(State state) {
		return 0;
	}

	public static Heuristic getHeuristic(String heuristic) {
		switch (heuristic) {
			case Constants.MANHATTAN:
				return Evaluator::manhattan;
			case Constants.GREEDY:
				return Evaluator::greedy;
			case Constants.UNIFORM:
				return Evaluator::uniform;
			default:
				return null;
		}
	}

	private static List<Pair<Integer, Integer>> createReferenceList() {
		List<Pair<Integer, Integer>> xyList = new ArrayList<>();
		int nByN = Utils.getN() * Utils.getN();
		int n = Utils.getN();
		int x = 0, y = 0, i = 0;

		while (i < nByN) {
			while (x < n) {
				xyList.add(new MutablePair<>(x, y));
				x++;
			}
			i += x;
			x = 0;
			y++;
		}
		return Collections.unmodifiableList(xyList);
	}
}
