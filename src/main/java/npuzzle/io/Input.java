package npuzzle.io;

import npuzzle.logic.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static npuzzle.utils.Constants.MANHATTAN;

public class Input {

	private String file, algorithm, heuristic;
	private int n;
	private boolean isRandom;
	private List<Integer> tiles;

	private static Input instance;

	private Input() {

	}

	public static Input getInstance() {
		if (instance == null)
			instance = new Input();
		return instance;
	}

	public boolean hasFile() {
		return file != null;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(String heuristic) {
		this.heuristic = heuristic;
	}

	public void setTilesAndN(List<Integer> tiles, int n) {
		this.tiles = tiles;
		this.n = n;
	}

	public List<Integer> getTiles() {
		return new ArrayList<>(tiles);
	}

	public int getN() {
		return n;
	}

	public void generateRandomTiles(int n) {
		this.n = n;
		isRandom = true;
		int nByN = n * n;

		tiles = new ArrayList<>(nByN);
		while (--nByN >= 0)
			tiles.add(nByN);

		do {
			Collections.shuffle(tiles);
		} while (!new State(tiles, MANHATTAN).isSolvable());
	}

	public boolean isRandom() {
		return isRandom;
	}

	@Override
	public String toString() {
		return String.format("Input: algorithm = %s; heuristic = %s; n = %d; isRandom = %b.", algorithm, heuristic, n, isRandom);
	}

}
