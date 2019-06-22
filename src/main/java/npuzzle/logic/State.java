package npuzzle.logic;

import com.google.common.collect.Comparators;
import npuzzle.utils.Constants;
import npuzzle.utils.StateMap;
import npuzzle.utils.Utils;

import java.util.*;

// TODO: create Executor with 'mode' and 'n' fields
// TODO: Solve the empty tile 0 or 9 problem.
public class State implements Comparable<State> {

	private List<Integer> tiles;
	private int indexOfEmpty;
	private final Evaluator.Heuristic evaluator;
	private final State parent;

	public State(String heuristic) {
		evaluator = Evaluator.getHeuristic(heuristic);
		parent = null;
	}

	public State(List<Integer> tiles) {
		this(Constants.MANHATTAN);
		setTiles(tiles);
	}

	public State(State other) {
		this.evaluator = other.evaluator;
		setTiles(new ArrayList<>(other.tiles));
		this.parent = other;
	}

//	TODO: cache this <- where and how?
	int evaluate() {
		return evaluator.evaluate(this);
	}

	List<State> createHierarchy() { // TODO: test this

		LinkedList<State> hierarchy = new LinkedList<>();

		for (State current = this; current != null; current = current.parent)
			hierarchy.addFirst(current);

		return hierarchy;
	}

	private int getRowOfEmpty() {
		return indexOfEmpty / Utils.getN();
	}

	private int getColumnOfEmpty() {
		return indexOfEmpty % Utils.getN();
	}

	private boolean isEmptyOnTopEdge() {
		return getRowOfEmpty() == 0;
	}

	private boolean isEmptyOnBottomEdge() {
		return getRowOfEmpty() == Utils.getN() - 1;
	}

	private boolean isEmptyOnLeftEdge() {
		return getColumnOfEmpty() == 0;
	}

	private boolean isEmptyOnRightEdge() {
		return getColumnOfEmpty() == Utils.getN() - 1;
	}

//	TODO: test. Was never tested
	StateMap createChildren() {
		StateMap children = new StateMap();

		// TODO: switch case?
		if (!isEmptyOnTopEdge()) // UP
			children.put(createChild(indexOfEmpty, indexOfEmpty - Utils.getN()));
		if (!isEmptyOnBottomEdge()) // DOWN
			children.put(createChild(indexOfEmpty, indexOfEmpty + Utils.getN()));
		if (!isEmptyOnLeftEdge()) // LEFT
			children.put(createChild(indexOfEmpty, indexOfEmpty - 1));
		if (!isEmptyOnRightEdge()) // RIGHT
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
		indexOfEmpty = tiles.indexOf(0);
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

	private int countInversions() { // TODO: (un)boxing optimization

		int inversions = 0;

		for (int i = 0; i < size() - 1; i++) {

			int a = tiles.get(i);
			if (a == 0)
				continue;

			for (int j = i + 1; j < size(); j++) {

				int b = tiles.get(j);
				if (b != 0 && a > b)
					inversions++;
			}
		}


		return inversions;
	}

	// TODO: test this
	public boolean isSolvable() {

		int n = Utils.getN();
		int inversions = countInversions();

		// if n is even
		if (n / 2 == 0) {

			int positionFromBottom = n - getRowOfEmpty() + 1;

			// if pos is even and inversions is odd
			// or
			// if pos is odd and inversions is even
			return (positionFromBottom / 2 + inversions / 2) == 1;
		}

		// if n is odd and inversions is even
		return inversions / 2 == 0;
	}

}
