package npuzzle.logic;

import com.google.common.collect.Comparators;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

import static npuzzle.utils.Constants.EMPTY;

// TODO: Solve the empty tile 0 or 9 problem.
// TODO: think of ways to reduce number of fields
public class State implements Comparable<State> {

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
		int tileNum = 0, capacity = n * n;
		List <Integer> tiles = new ArrayList<>(Collections.nCopies(capacity, 0));

		int i = 0, j = -1;
		boolean isDecrement = false, isMainI = false;

		while (tileNum + 1 < capacity) {
			System.out.printf("i = %d, j = %d, tileNum = %d%n", i, j, tileNum);

			int change = isDecrement ? -1: 1;
			if (isMainI)
				i += change;
			else
				j += change;

			tileNum++;

			tiles.set(i * n + j, tileNum);
			System.out.printf("tiles.set(%d * %d + %d = [%d], %d)%n", i, n, j, i * n + j,tileNum);


			if ((isDecrement && main == 0) || (!isDecrement && main == n - 1)) {
				isMainI = !isMainI;
				main = isMainI ? i: j;
				System.out.printf("changed isMainI to %b%n", isMainI);
				if ((isDecrement && main == 0) || (!isDecrement && main == n - 1))
					isDecrement = !isDecrement;
					System.out.printf("changed isDecrement to %b%n", isDecrement);
			}

		}

		return new State(tiles, "invalid");
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
		int indexOfEmpty = tiles.indexOf(EMPTY);

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
		child.tiles.set(j, EMPTY);

		return child;
	}

//	TODO: re-implement
	public boolean isNotSolvable() {
		return Utils.isNotSolvable(tiles, n);
	}

	List<State> collectPath() {
		LinkedList<State> path = new LinkedList<>();

		for (State current = this; current != null; current = current.parent)
			path.addFirst(current);

		return path;
	}

//	TODO: re-implement
	@SuppressWarnings("UnstableApiUsage")
	boolean isNotFinal() {
		return !Comparators.isInOrder(tiles, Comparator.naturalOrder());
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
		return tiles.toString() + " evaluation: " + evaluation;
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
