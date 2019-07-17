package npuzzle.io;

import npuzzle.logic.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static npuzzle.utils.Constants.MANHATTAN;

public class Input {

	private final String[] args;

	private String file, algorithm, heuristic;
	private int n;
	private boolean isRandom;
	private List<Integer> tiles;
	private State initialState;

	private Input(String[] args) {
		this.args = args;
	}

	private Input(List<Integer> tiles, int n, String algorithm, String heuristic, State initialState) {
		this.tiles = tiles;
		this.n = n;
		this.algorithm = algorithm;
		this.heuristic = heuristic;
		this.initialState = initialState;
		this.args = null;
	}

	static Input fromArgs(String[] args) {
		return new Input(args);
	}

	public static Input create(List<Integer> tiles, int n, String algorithm, String heuristic) {
		return new Input(tiles, n, algorithm, heuristic, State.createFrom(tiles, heuristic));
	}

	boolean hasFile() {
		return file != null;
	}

	String getFile() {
		return file;
	}

	void setFile(String file) {
		this.file = file;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	String getHeuristic() {
		return heuristic;
	}

	void setHeuristic(String heuristic) {
		this.heuristic = heuristic;
	}

	void setTilesAndN(List<Integer> tiles, int n) {
		this.tiles = tiles;
		this.n = n;
	}

	List<Integer> getTiles() {
		return new ArrayList<>(tiles);
	}

	void generateRandomTiles(int n) {
		this.n = n;
		isRandom = true;
		int nByN = n * n;

		tiles = new ArrayList<>(nByN);
		while (--nByN >= 0)
			tiles.add(nByN);

		do Collections.shuffle(tiles);
			while (State.createFrom(tiles, MANHATTAN).isNotSolvable());
	}

	boolean isRandom() {
		return isRandom;
	}

	String[] getArgs() {
		return args;
	}

	public State getInitialState() {
		return initialState;
	}

	void setInitialState(State initial) {
		this.initialState = initial;
	}

	@Override
	public String toString() {
		return String.format("Algorithm = %s; heuristic = %s; n = %d; isRandom = %b.", algorithm, heuristic, n, isRandom);
	}

}
