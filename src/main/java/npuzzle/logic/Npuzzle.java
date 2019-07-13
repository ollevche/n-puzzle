package npuzzle.logic;

import npuzzle.io.Input;
import npuzzle.io.Reader;
import npuzzle.io.Writer;

import java.util.Objects;

public class Npuzzle implements Runnable {

	private final Input input;

	private Npuzzle(Input input) {
		this.input = input;
	}

	public static Npuzzle create(Input input) {
		return new Npuzzle(input);
	}

	@Override
	public void run() {

//		use reader to populate the Input
//		get the starting state from input
//		get the executor from input

		if (!Reader.createWith(input).fillInput()) {
			Writer.write("Failed for Input:" + input);
			return;
		}

		State startingState = State.createFrom(input.getTiles(), input.getHeuristic());
		Executor.Algorithm executor = Objects.requireNonNull(Executor.getAlgorithm(input.getAlgorithm()));

		if (startingState.isNotSolvable()) { // TODO: move this to the validator or somewhere else
			Writer.write("Sorry. This one is unsolvable.");
			return;
		}

		Writer.write(executor.execute(startingState), true);
	}

}
