package npuzzle;

import npuzzle.io.Input;
import npuzzle.io.Output;
import npuzzle.logic.Npuzzle;
import npuzzle.logic.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static npuzzle.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NpuzzleTest {

	private static final State finalOfThree = State.createFrom(Arrays.asList(1, 2, 3, 8, 0, 4, 7, 6, 5), "");
	private static final State finalOfFour = State.createFrom(Arrays.asList(1, 2, 3, 4, 12, 13, 14, 5, 11, 0, 15, 6, 10, 9, 8, 7), "");
	private static final State finalOfFive = State.createFrom(Arrays.asList(1, 2, 3, 4, 5, 16, 17, 18, 19, 6, 15, 24, 0, 20, 7, 14, 23, 22, 21, 8, 13, 12, 11, 10, 9), "");
	private static final Map<Integer, State> finals = new HashMap<>() {{
		put(3, finalOfThree);
		put(4, finalOfFour);
		put(5, finalOfFive);
	}};

	@Disabled("greedy not implemented")
	@Test void threesManhattanGreedy() {
		testAlgorithm(3, GREEDY, MANHATTAN, 1, 10, TimeUnit.SECONDS);
	}

	@Test void threesManhattanUniform() {
		testAlgorithm(3, UNIFORM, MANHATTAN, 1, 10, TimeUnit.SECONDS);
	}

	@Test void threesManhattanAstar() {
		testAlgorithm(3, ASTAR, MANHATTAN, 1, 10, TimeUnit.SECONDS);
	}

	@Disabled("greedy not implemented")
	@Test void threesEuclideanGreedy() {
		testAlgorithm(3, GREEDY, EUCLIDEAN, 1, 10, TimeUnit.SECONDS);
	}

	@Test void threesEuclideanUniform() {
		testAlgorithm(3, UNIFORM, EUCLIDEAN, 2, 11, TimeUnit.SECONDS);
	}

	@Test void threesEuclideanAstar() {
		testAlgorithm(3, ASTAR, EUCLIDEAN, 1, 10, TimeUnit.SECONDS);
	}

	void testAlgorithm(int n, String a, String h, long minTotalTimeout, long individualTimeout, TimeUnit individualTimeUnit) {
		String[] args = String.format("-r %d -a %s -h %s", n, a, h).split(" ");
		ExecutorService service = Executors.newFixedThreadPool(7);
		List<Npuzzle> puzzles = new ArrayList<>();

		for (int i = 0; i < 7; i++) {
			Input input = new Input(args);
			puzzles.add(Npuzzle.create(input));
		}

		List<Executable> list = new ArrayList<>();
		try {
			for (Future<Output> future : service.invokeAll(puzzles, minTotalTimeout, TimeUnit.MINUTES)) {
				State received = future.get().getFinal();
				State expected = finals.get(n);
				boolean inTime = future.get().getStopwatch().elapsed(individualTimeUnit) < individualTimeout;
				list.add(() -> assertEquals(expected, received, "Wrong final"));
				list.add(() -> assertTrue(inTime, badTime(future.get(), individualTimeout, individualTimeUnit)));
			}
		} catch (InterruptedException | ExecutionException ex) {
			ex.printStackTrace();
		} catch (CancellationException e) {
			System.err.println("Killed due to long wait time");
		} finally {
			service.shutdown();
		}
		Assertions.assertAll(list);

	}

	private Supplier<String> badTime(Output output, long timeExpected, TimeUnit unit) {
		return () -> {
			TestUtils.logToJson(output, timeExpected, unit);
			return String.format("Killed. Starting state: %s. Time elapsed: %s s. Time expected: %s",
					output.getInput().getInitialState(), output.getStopwatch().elapsed(unit), timeExpected);
		};
	}

}
