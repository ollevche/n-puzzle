package npuzzle;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import npuzzle.io.Input;
import npuzzle.io.Reader;
import npuzzle.logic.Evaluator;
import npuzzle.logic.Npuzzle;

public class App {

	public static void main(String[] args) {
		Stopwatch stopwatch = Stopwatch.createStarted();

		if (!Reader.readInput(args))
			return;

		System.out.println(Input.getInstance().toString());

		Evaluator.createReferenceList();
		if (!Npuzzle.execute())
			return;

		System.out.println("Executed successfully. Elapsed:" + stopwatch.elapsed(TimeUnit.MICROSECONDS));
	}

}
