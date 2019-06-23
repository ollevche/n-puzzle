package npuzzle.logic;

import npuzzle.io.Input;
import npuzzle.io.Writer;

public class Npuzzle {

	private Npuzzle() {

	}

	private static State createStartingState() {
		return new State(Input.getInstance().getTiles(), Input.getInstance().getHeuristic());
	}

	private static Executor.Algorithm pickExecutor() {
		return Executor.getAlgorithm(Input.getInstance().getAlgorithm());
	}

	public static boolean Execute() {

		State startingState = createStartingState();
		Executor.Algorithm algorithm = pickExecutor();

		if (!startingState.isSolvable()) {
			System.out.println("Sorry. This one is unsolvable.");
			return false;
		}

		Writer.write(algorithm.execute(startingState));

		return true;
	}

}
