package npuzzle.logic;

import com.google.common.collect.Comparators;
import npuzzle.utils.Constants;
import npuzzle.utils.StateMap;
import npuzzle.utils.Utils;

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

//	TODO: cache this <- where and how?
	int evaluate() {
		return evaluator.evaluate(this);
	}

	List<State> createHierarchy() {
		if (isRoot())
			return new LinkedList<>();

		List<State> hierarchy = parent.createHierarchy();

		hierarchy.add(this);
		return hierarchy;
	}

//	TODO: test. Was never tested
	StateMap createChildren() {
		StateMap children = new StateMap();
		int indexOfEmpty = tiles.indexOf(0);

		if (indexOfEmpty / Utils.getN() != 0) // UP
			children.put(createChild(indexOfEmpty, indexOfEmpty - Utils.getN()));
		if (indexOfEmpty / Utils.getN() != Utils.getN() - 1) // DOWN
			children.put(createChild(indexOfEmpty, indexOfEmpty + Utils.getN()));
		if (indexOfEmpty % Utils.getN() != 0) // LEFT
			children.put(createChild(indexOfEmpty, indexOfEmpty - 1));
		if (indexOfEmpty % Utils.getN() != Utils.getN() - 1) // RIGHT
			children.put(createChild(indexOfEmpty, indexOfEmpty + 1));

		return children;
	}

	private Map.Entry<String, State> createChild(int i, int j) {
		State child = new State(this);

		child.tiles.set(i, child.tiles.get(j));
		child.tiles.set(j, 0);
		return new AbstractMap.SimpleEntry<>(child.toString(), child);
	}

	@SuppressWarnings("UnstableApiUsage")
	boolean isFinal() {
		return Comparators.isInOrder(tiles, Comparator.naturalOrder());
	}

	private boolean isRoot() {
		return parent == null;
	}

	public List<Integer> getTiles() {
		return tiles;
	}

	public void setTiles(List<Integer> tiles) {
		this.tiles = tiles;
	}

	@Override
	public int compareTo(State o) {
		return evaluate() - o.evaluate();
	}

	@Override
	public String toString() {
		return tiles.toString();
	}

	public int size() {
		return tiles.size();
	}
}
