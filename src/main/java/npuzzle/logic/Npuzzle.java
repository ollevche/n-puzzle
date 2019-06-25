package npuzzle.logic;

import npuzzle.io.Input;
import npuzzle.io.Writer;

public class Npuzzle {

	private Npuzzle() {

	}

	public static boolean execute() {
		State startingState = createStartingState();
		Executor.Algorithm executor = Executor.getAlgorithm(Input.getInstance().getAlgorithm());

		if (!startingState.isSolvable()) {
			System.out.println("Sorry. This one is unsolvable.");
			return false;
		}

		Writer.write(executor.execute(startingState), true);

		return true;
	}

	private static State createStartingState() {
		return new State(Input.getInstance().getTiles(), Input.getInstance().getHeuristic());
	}

}
