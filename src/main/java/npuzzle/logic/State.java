package npuzzle.logic;

import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;
import java.util.function.BiConsumer;

import static npuzzle.utils.Constants.NO_TILE;

public class State implements Comparable<State> {

	public static final State EMPTY = new State(Collections.emptyList(), StringUtils.EMPTY);
	private final Evaluator.Heuristic evaluator;
	private final List<Integer> tiles;
	private final int n;
	private int evaluation;
	private int pathSize;
	private State parent;

	private State(List<Integer> tiles, String heuristic) {
		this.tiles = tiles;
		this.evaluator = Evaluator.getHeuristic(heuristic);
		this.parent = null;
		this.pathSize = 0;
		this.n = (int) Math.sqrt(tiles.size());
	}

	public static State createFinal(int n) {
        int tileNum = 1, capacity = n * n;
		List <Integer> tiles = new ArrayList<>(Collections.nCopies(capacity, 0));
		BiConsumer<Integer, Integer> setTile = (index, tile) -> {if (tile < capacity) tiles.set(index, tile);};

        int min = 0, max = n - 1;
        while (tileNum < capacity) {
        	// from left to right
            for (int j = min; j < max; j++)
                setTile.accept(min * n + j, tileNum++);
            // from top to bottom
            for (int i = min; i < max; i++)
				setTile.accept(i * n + max, tileNum++);
            // from right to left
            for (int j = max; j > min; j--)
				setTile.accept(max * n + j, tileNum++);
            // from bottom to top
            for (int i = max; i > min; i--)
				setTile.accept(i * n + min, tileNum++);

			min++;
			max--;
		}

		return new State(tiles, "none");
	}

	public static State createFrom(List<Integer> tiles, String heuristic) {
		return new State(tiles, heuristic);
	}

	private State(State other) {
		this.tiles = new ArrayList<>(other.tiles);
		this.evaluator = other.evaluator;
		this.parent = other.parent;
		this.pathSize = other.pathSize;
		this.n = other.n;
	}

	private static State childOf(State parent) {
		State child = new State(parent);
		child.parent = parent;
		child.pathSize++;
		return child;
	}

	private int evaluate() {
		if (evaluation == 0 && evaluator != null)
			evaluation = evaluator.evaluate(this, n) + pathSize;
		else if (evaluation == 0)
			evaluation = pathSize;
		return evaluation;
	}

	Set<State> createChildren() {
		Set<State> children = new HashSet<>();
		int indexOfEmpty = tiles.indexOf(NO_TILE);

		if (Utils.canMoveUp(indexOfEmpty, n)) // UP
			children.add(createChild(indexOfEmpty, indexOfEmpty - n));
		if (Utils.canMoveDown(indexOfEmpty, n)) // DOWN
			children.add(createChild(indexOfEmpty, indexOfEmpty + n));
		if (Utils.canMoveLeft(indexOfEmpty, n)) // LEFT
			children.add(createChild(indexOfEmpty, indexOfEmpty - 1));
		if (Utils.canMoveRight(indexOfEmpty, n)) // RIGHT
			children.add(createChild(indexOfEmpty, indexOfEmpty + 1));

		return children;
	}

	private State createChild(int i, int j) {
		State child = childOf(this);

		child.tiles.set(i, child.tiles.get(j));
		child.tiles.set(j, NO_TILE);

		return child;
	}

//	TODO: re-implement
	public boolean isNotSolvable() {
		return !true;//Utils.isNotSolvable(tiles, n);
	}

	List<State> collectPath() {
		LinkedList<State> path = new LinkedList<>();

		for (State current = this; current != null; current = current.parent)
			path.addFirst(current);

		return path;
	}

	@SuppressWarnings("UnstableApiUsage")
	boolean isNotFinal() {
		return !equals(Evaluator.getFinal(n));
	}

	/**
	 * violates the contract between {@link #equals}
	 */
	@Override public int compareTo(@NonNull State o) {
		return Integer.compare(evaluate(), o.evaluate());
	}

	/**
	 * violates the contract between {@link #compareTo}
	 */
	@Override public boolean equals(Object obj) {
		if (obj != null && obj.getClass().equals(State.class))
			return tiles.equals(((State) obj).tiles);
		return false;
	}

	@Override public int hashCode() {
		return tiles.hashCode();
	}

	@Override public String toString() {
		return tiles.toString() + " | evaluation: " + evaluation;
	}

	public List<Integer> getTiles() {
		return tiles;
	}

	public int getN() {
		return n;
	}

	private static class Utils {
		private static int getRowOfEmpty(int indexOfEmpty, int n) {
			return indexOfEmpty / n;
		}

		private static int getColumnOfEmpty(int indexOfEmpty, int n) {
			return indexOfEmpty % n;
		}

		private static boolean canMoveUp(int indexOfEmpty, int n) {
			return getRowOfEmpty(indexOfEmpty, n) != 0;
		}

		private static boolean canMoveDown(int indexOfEmpty, int n) {
			return getRowOfEmpty(indexOfEmpty, n) != n - 1;
		}

		private static boolean canMoveLeft(int indexOfEmpty, int n) {
			return getColumnOfEmpty(indexOfEmpty, n) != 0;
		}

		private static boolean canMoveRight(int indexOfEmpty, int n) {
			return getColumnOfEmpty(indexOfEmpty, n) != n - 1;
		}

		// TODO: re-implement
		private static boolean isNotSolvable(List<Integer> tiles, int n) {
			int inversions = countInversions(tiles);
			int indexOfEmpty = tiles.indexOf(EMPTY);
			// if n is even
			if (n % 2 == 0) {

				int positionFromBottom = n - Utils.getRowOfEmpty(indexOfEmpty, n);
			/* 	if pos is even and inversions is odd
			 	or
			 	if pos is odd and inversions is even
			 */
				return (positionFromBottom % 2 + inversions % 2) != 1;
			}

			// if n is odd and inversions is even
			return inversions % 2 != 0;
		}

		private static int countInversions(List<Integer> tiles) {
			int inversions = 0;

			for (int i = 0; i < tiles.size() - 1; i++) {

				int a = tiles.get(i);
				if (a == 0)
					continue;

				for (int j = i + 1; j < tiles.size(); j++) {

					int b = tiles.get(j);
					if (b != 0 && a > b)
						inversions++;
				}
			}

			return inversions;
		}

	}

}
