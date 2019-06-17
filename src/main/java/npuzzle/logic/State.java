package npuzzle.logic;

import com.google.common.collect.Comparators;
import npuzzle.utils.Constants;
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

	// TODO: this
	public TreeMap<String, State> createChildren() {
		TreeMap<String, State> children = new TreeMap<>();
		int indexOfEmpty = tiles.indexOf(0);
		List<Integer> tilesCopy = new ArrayList<>(tiles);

		if (indexOfEmpty / Utils.getN() != 0) { // UP
//			children.put(createChild(indexOfEmpty, indexOfEmpty - Utils.getN())); // если будет свой меп
			/*tilesCopy.set(indexOfEmpty, tilesCopy.get(indexOfEmpty - Utils.getN()));
			tilesCopy.set(indexOfEmpty - Utils.getN(), 0);*/
		}
		if (indexOfEmpty / Utils.getN() != Utils.getN() - 1) { // DOWN
//			State s = createChild(indexOfEmpty, indexOfEmpty - Utils.getN());
//			children.put(s.getTiles().toString(), s);
			/*tilesCopy.set(indexOfEmpty, tilesCopy.get(indexOfEmpty + Utils.getN()));
			tilesCopy.set(indexOfEmpty + Utils.getN(), 0);*/
		}
		if (indexOfEmpty % Utils.getN() != 0) { // LEFT
			Map.Entry<String, State> child = createChild(indexOfEmpty, indexOfEmpty - Utils.getN());
			children.put(child.getKey(), child.getValue());
			/*tilesCopy.set(indexOfEmpty, tilesCopy.get(indexOfEmpty + Utils.getN()));
			tilesCopy.set(indexOfEmpty + Utils.getN(), 0);*/
		}
		if (indexOfEmpty % Utils.getN() != Utils.getN() - 1) { // RIGHT
			tilesCopy.set(indexOfEmpty, tilesCopy.get(indexOfEmpty + Utils.getN()));
			tilesCopy.set(indexOfEmpty + Utils.getN(), 0);
		}

		return children;
	}

	private Map.Entry<String, State> createChild(int i, int j) {
		State child = new State(this);

		child.tiles.set(i, child.tiles.get(j));
		child.tiles.set(j, 0);
		return new AbstractMap.SimpleEntry<>(child.tiles.toString(), child);
	}

}
