package npuzzle;

import npuzzle.io.Input;
import npuzzle.io.Output;
import npuzzle.io.Writer;
import npuzzle.logic.Npuzzle;
import npuzzle.logic.State;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUtils {

	private TestUtils() {
		throw new AssertionError();
	}

	private static final State finalOfThree = State.createFrom(Arrays.asList(1, 2, 3, 8, 0, 4, 7, 6, 5), "");
	private static final State finalOfFour = State.createFrom(Arrays.asList(1, 2, 3, 4, 12, 13, 14, 5, 11, 0, 15, 6, 10, 9, 8, 7), "");
	private static final State finalOfFive = State.createFrom(Arrays.asList(1, 2, 3, 4, 5, 16, 17, 18, 19, 6, 15, 24, 0, 20, 7, 14, 23, 22, 21, 8, 13, 12, 11, 10, 9), "");
	private static final Map<Integer, State> finals = new HashMap<Integer, State>() {{
		put(3, finalOfThree);
		put(4, finalOfFour);
		put(5, finalOfFive);
	}};

	static void testRandom(int times, int n, String a, String h, long minTotalTimeout) {
		String[] args = String.format("-r %d -a %s -h %s", n, a, h).split(" ");
		List<Npuzzle> puzzles = new ArrayList<>();
		List<Executable> list = new ArrayList<>();

		for (int i = 0; i < times; i++) puzzles.add(Npuzzle.create(new Input(args)));

		ExecutorService service = Executors.newFixedThreadPool(times);
		try {
			for (Future<Output> future : service.invokeAll(puzzles, minTotalTimeout, TimeUnit.MINUTES)) {
				State received = future.get().getFinal();
				State expected = finals.get(n);
				list.add(() -> assertEquals(expected, received, "WRONG FINAL"));
			}
		} catch (InterruptedException | ExecutionException ex) {
			ex.printStackTrace();
		} catch (CancellationException e) {
			System.err.printf("Killed: took too long.%n%n");
			System.err.printf("Puzzles were%n%s%n", puzzles);
			for (Npuzzle p : puzzles) logToJson(p);
		} finally {
			service.shutdown();
		}
		Assertions.assertAll(list);
	}

	private static void logToJson(Npuzzle puzzle) {
		Output output = puzzle.output();
		Input input = puzzle.input();
		if (input != null)
			if (output == null || !output.getFinal().equals(State.createFinal(input.getN()))) {
				JSONObject o = createLogJson(input);
				Writer.writeJson(o.toString(), "src\\test\\resources\\failedTime.json");
			}
	}

	private static JSONObject createLogJson(Input input) {
		JSONObject o = new JSONObject();
		o.put("a", input.getAlgorithm());
		o.put("h", input.getHeuristic());
		o.put("n", input.getN());
		o.put("initial", input.getInitialState());
		return o;
	}

}
