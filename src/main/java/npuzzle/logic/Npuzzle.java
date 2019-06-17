package npuzzle.logic;

import npuzzle.io.Writer;

import java.util.List;

public class Npuzzle {

	private final Executor.Algorithm executor;
	private final State startingState;

	public Npuzzle(String algorithm, State startingState) {
		executor = Executor.getAlgorithm(algorithm);
		this.startingState = startingState;
	}

	public void execute() {
		List<State> solution = executor.execute(startingState);
		Writer.write(solution);
	}

}
