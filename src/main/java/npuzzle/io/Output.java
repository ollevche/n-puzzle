package npuzzle.io;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import npuzzle.logic.State;

import java.util.List;

public class Output {

	private final int everInOpenSet;
	private final int maxNumberOfSates;
	private final List<State> path;
	private Stopwatch stopwatch;
	private Input input;

	private Output(int everInOpenSet, int maxNumberOfSates, List<State> path) {
		this.everInOpenSet = everInOpenSet;
		this.maxNumberOfSates = maxNumberOfSates;
		this.path = path;
	}

	public static Output create(int everInOpenSet, int maxNumberOfSates, List<State> path) {
		return new Output(everInOpenSet, maxNumberOfSates, path);
	}

	public int getEverInOpenSet() {
		return everInOpenSet;
	}

	public int getMaxNumberOfSates() {
		return maxNumberOfSates;
	}

	List<State> getPath() {
		return path;
	}

	public State getFinal() {
		return Iterables.getLast(path, State.EMPTY);
	}

	@Override public String toString() {
		return "Number of states ever in the opened set (complexity in time): " + everInOpenSet
				+ ".\nMaximum number of states ever represented in memory at the same time (complexity in size): " + maxNumberOfSates
				+ ".\nNumber of moves required to transition from the initial state to the final state: " + (path.size() - 1)
				+ ".\nTotal time elapsed: " + stopwatch;
	}

	public Output setStopwatch(Stopwatch stopwatch) {
		this.stopwatch = stopwatch;
		return this;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Input getInput() {
		return input;
	}
}
