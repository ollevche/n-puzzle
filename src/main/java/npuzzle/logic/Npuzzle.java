package npuzzle.logic;

import com.google.common.base.Stopwatch;
import npuzzle.io.*;

import java.util.Objects;
import java.util.concurrent.Callable;

public class Npuzzle implements Callable<Output> {

	private final Input input;
	private Output output;

	private Npuzzle(Input input) {
		this.input = input;
	}

	public static Npuzzle create(Input input) {
		return new Npuzzle(input);
	}

	@Override
	public Output call() {
		Stopwatch stopwatch = Stopwatch.createStarted();

		if (!Reader.createWith(input).fillInput())
			return null;

		State initial = input.getInitialState();
		Evaluator.addReferenceList(initial.getN());
		Executor.Algorithm executor = Objects.requireNonNull(Executor.getAlgorithm(input.getAlgorithm()));
		output = executor.execute(initial);
		output.setStopwatch(stopwatch.stop()).setInput(input);
		Writer.write(input, output, false);
		return output;
	}

	public Output output() {
		return output;
	}

	public Input input() {
		return input;
	}

	@Override public String toString() {
		return String.format("Initial state: %s%nFinal state: %s%na: %s, h: %s, n: %d%n%n",
				input.getInitialState(), output == null ? "" : output.getFinal(), input.getAlgorithm(), input.getHeuristic(), input.getN());
	}
}
