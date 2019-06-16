package npuzzle.logic;

import npuzzle.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class State implements Comparable<State> {
	private String mode;
	private List<Integer> tiles;
	private final Evaluator.EvaluateInterface evaluator;

	public State(String mode) {
		this.mode = mode;
		evaluator = Evaluator.getInterface(mode);
	}

	public State(List<Integer> tiles) {
		this(Constants.ASTAR);
		this.tiles = tiles;
	}

	public State(State other) {
		this.mode = other.mode;
		this.evaluator = other.evaluator;
		this.tiles = new ArrayList<>(other.tiles);
	}

	public int evaluate() {
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
