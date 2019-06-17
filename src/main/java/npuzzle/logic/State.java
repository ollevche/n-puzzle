package npuzzle.logic;

import com.google.common.collect.Comparators;
import npuzzle.utils.Constants;

import java.util.*;

// TODO: create Executor with 'mode' and 'n' fields

public class State implements Comparable<State> {

	private List<Integer> tiles;
	private final Evaluator.Heuristic evaluator;
	private final State parent;

	public State(String heuristic) {
		evaluator = Evaluator.getHeuristic(heuristic);
		parent = null;
	}

	public State(List<Integer> tiles) {
		this(Constants.MANHATTAN);
		this.tiles = tiles;
	}

	public State(State other) {
		this.evaluator = other.evaluator;
		this.tiles = new ArrayList<>(other.tiles);
		this.parent = other;
	}

	public int evaluate() { // TODO: cache this
		return evaluator.evaluate(this);
	}

	@Override
	public int compareTo(State o) {
		return evaluate() - o.evaluate();
	}

	public List<Integer> getTiles() {
		return tiles;
	}

	public void setTiles(List<Integer> tiles) {
		this.tiles = tiles;
	}

	@Override
	public String toString() {
		return tiles.toString();
	}

	public int size() {
		return tiles.size();
	}

	public boolean isFinal() {
		return Comparators.isInOrder(tiles, Comparator.naturalOrder());
	}

	public boolean isRoot() {
		return parent == null;
	}

	public List<State> createHierarchy() {
		if (isRoot())
			return new LinkedList<>();

		List<State> hierarchy = parent.createHierarchy();

		hierarchy.add(this);
		return hierarchy;
	}

	public TreeMap<String, State> createChildren() {
		return null; // TODO: this
	}

}
