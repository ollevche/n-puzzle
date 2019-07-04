package npuzzle.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.google.common.collect.Comparators;

import npuzzle.io.Input;

// TODO: Solve the empty tile 0 or 9 problem.
public class State implements Comparable<State> {

	private List<Integer> tiles;
	private int indexOfEmpty;
	private final Evaluator.Heuristic evaluator;
	private final State parent;
	private int evaluation = -1;

	public State(List<Integer> tiles, String heuristic) {
		this.tiles = tiles;
		this.indexOfEmpty = tiles.indexOf(0);
		this.evaluator = Evaluator.getHeuristic(heuristic);
		this.parent = null;
	}

	public State(State other, State parent) {
		this.tiles = new ArrayList<>(other.tiles);
		this.indexOfEmpty = other.indexOfEmpty;
		this.evaluator = other.evaluator;
		this.parent = parent;
	}

	//	TODO: cache this <- where and how?
	int evaluate() {
		if (evaluation == -1)
			evaluation = evaluator.evaluate(this);
		return evaluation;
	}

	List<State> createChildren() {
		List<State> children = new ArrayList<>();
		int n = Input.getInstance().getN();

		if (!isEmptyOnTopEdge()) // UP
			children.add(createChild(indexOfEmpty, indexOfEmpty - n));
		if (!isEmptyOnBottomEdge()) // DOWN
			children.add(createChild(indexOfEmpty, indexOfEmpty + n));
		if (!isEmptyOnLeftEdge()) // LEFT
			children.add(createChild(indexOfEmpty, indexOfEmpty - 1));
		if (!isEmptyOnRightEdge()) // RIGHT
			children.add(createChild(indexOfEmpty, indexOfEmpty + 1));

		return children;
	}

	private State createChild(int i, int j) {
		State child = new State(this, this);

		child.tiles.set(i, child.tiles.get(j));
		child.tiles.set(j, 0);
		child.indexOfEmpty = j;

		return child;
	}

	// TODO: (un)boxing optimization
	private int countInversions() {
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

	public boolean isSolvable() {
		int inversions = countInversions();
		int n = Input.getInstance().getN();

		// if n is even
		if (n % 2 == 0) {

			int positionFromBottom = n - getRowOfEmpty();
			/* 	if pos is even and inversions is odd
			 	or
			 	if pos is odd and inversions is even */
			return (positionFromBottom % 2 + inversions % 2) == 1;
		}

		// if n is odd and inversions is even
		return inversions % 2 == 0;
	}

	List<State> collectPath() {
		LinkedList<State> path = new LinkedList<>();

		for (State current = this; current != null; current = current.parent)
			path.addFirst(current);

		return path;
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

	// TODO: fix compareTo
	@Override
	public int compareTo(@NonNull State o) {
		return evaluate() - o.evaluate();
	}

	@Override public boolean equals(Object obj) {
		if (obj != null && obj.getClass().equals(State.class))
			return tiles.equals(((State)obj).tiles);
		return false;
	}

	@Override public int hashCode() {
		return tiles.hashCode();
	}

	@Override
	public String toString() {
		return tiles.toString();
	}

	public int size() {
		return tiles.size();
	}

	private int getRowOfEmpty() {
		return indexOfEmpty / Input.getInstance().getN();
	}

	private int getColumnOfEmpty() {
		return indexOfEmpty % Input.getInstance().getN();
	}

	private boolean isEmptyOnTopEdge() {
		return getRowOfEmpty() == 0;
	}

	private boolean isEmptyOnBottomEdge() {
		return getRowOfEmpty() == Input.getInstance().getN() - 1;
	}

	private boolean isEmptyOnLeftEdge() {
		return getColumnOfEmpty() == 0;
	}

	private boolean isEmptyOnRightEdge() {
		return getColumnOfEmpty() == Input.getInstance().getN() - 1;
	}

	void incrementEvaluation() {
		if (evaluation != -1)
			evaluation++;
	}

}
