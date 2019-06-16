package npuzzle.logic;

import npuzzle.utils.Constants;

import java.util.ArrayList;
import java.util.List;

// TODO: create Executor with 'mode' and 'n' fields

public class State implements Comparable<State> {

	private List<Integer> tiles;
	private final Evaluator.Heuristic evaluator;

	public State(String heuristic) {
		evaluator = Evaluator.getHeuristic(heuristic);
	}

	public State(List<Integer> tiles) {
		this(Constants.MANHATTAN);
		this.tiles = tiles;
	}

	public State(State other) {
		this.evaluator = other.evaluator;
		this.tiles = new ArrayList<>(other.tiles);
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

}
