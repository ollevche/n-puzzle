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

		State initial = input.getInitialState();
		Evaluator.addReferenceList(initial.getN());
		Executor.Algorithm executor = Objects.requireNonNull(Executor.getAlgorithm(input.getAlgorithm()));
		Output output = executor.execute(initial);
		output.setStopwatch(stopwatch);
		Writer.write(input, output, true);
	}

}
