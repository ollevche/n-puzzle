package npuzzle.logic;

import com.google.common.base.Stopwatch;
import npuzzle.io.*;

import java.util.Objects;
import java.util.concurrent.Callable;

public class Npuzzle implements Callable<Output> {

	private final Input input;

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
		Output output = executor.execute(initial);
		output.setStopwatch(stopwatch.stop()).setInput(input);
		Writer.write(input, output, true);
		return output;
	}

}
