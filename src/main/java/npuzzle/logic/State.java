package npuzzle.logic;

import npuzzle.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class State implements Comparable<State>
{
	private List<Integer> tiles;
	private String mode;

	public State(){}

	public State(State other) {
		tiles = new ArrayList<>(other.tiles);
		mode = other.mode;
	}

	public State(List<Integer> tiles) {
		this.tiles = tiles;
	}

//	TODO: these modes should be replaced by 3 heuristic functions. Constants should determine steps higher
	public int evaluate() {
		switch (mode) {
			case Constants.MANHATTAN :
				return Evaluator.evaluateManhattan();
			case Constants.GREEDY :
				return Evaluator.evaluateGreedy();
			case Constants.UNIFORM :
				return Evaluator.evaluateUniform();
		}
		return 0;
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
