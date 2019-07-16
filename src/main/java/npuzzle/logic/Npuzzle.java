package npuzzle.logic;

import com.google.common.base.Stopwatch;
import npuzzle.io.*;

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
		Stopwatch stopwatch = Stopwatch.createStarted();

		if (!Reader.createWith(input).fillInput())
			return;

		Executor.Algorithm executor = Objects.requireNonNull(Executor.getAlgorithm(input.getAlgorithm()));
		Output output = executor.execute(input.getInitialState());
		output.setStopwatch(stopwatch);
		Writer.write(input, output);
	}

}
