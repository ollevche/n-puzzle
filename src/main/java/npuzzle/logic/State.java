package npuzzle.logic;

import java.util.ArrayList;
import java.util.List;

public class State
{
	private List<Integer> tiles;

	public State(){}

	public State(State other) {
		this.tiles = new ArrayList<>(other.tiles);
	}

	public State(List<Integer> tiles) {
		this.tiles = tiles;
	}

	public List<Integer> getTiles() {
		return tiles;
	}

	public void setTiles(List<Integer> tiles) {
		this.tiles = tiles;
	}
}
