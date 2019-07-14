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
		if (!Reader.createWith(input).fillInput())
			return;

		Executor.Algorithm executor = Objects.requireNonNull(Executor.getAlgorithm(input.getAlgorithm()));
		Writer.write(executor.execute(input.getInitialState()), true);
	}

}
