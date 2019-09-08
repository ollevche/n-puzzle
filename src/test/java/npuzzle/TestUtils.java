package npuzzle;

import npuzzle.io.Input;
import npuzzle.io.Output;
import npuzzle.io.Reader;
import npuzzle.io.Writer;
import npuzzle.logic.Npuzzle;
import npuzzle.logic.State;
import npuzzle.utils.Error;
import npuzzle.utils.InvalidInputException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

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

    static void testManual(List<Integer> tiles, String a, String h) {
        int n = (int) Math.sqrt(tiles.size());
        Input input = Input.create(tiles, n, a, h);
        Npuzzle npuzzle = Npuzzle.create(input);
        Output output = npuzzle.call();
        assertEquals(finals.get(n), output.getFinal(), wrongFinal(output.getInput(), output));
    }

    static void testSolvability(String file) {
        String[] args = String.format("-f %s -a uniform -h manhattan", file).split(" ");
        Input input = new Input(args);
        input.setFile(file);
        Reader reader = Reader.createWith(input);
        reader.fillInput();
        if (input.getInitialState() == null)
            throw new InvalidInputException(Error.UNSOLVABLE);
    }

    static void testValidation(String file) throws IOException {
        String[] args = String.format("-f %s -a uniform -h manhattan", file).split(" ");
        Input input = new Input(args);
        input.setFile(file);
        Reader reader = Reader.createWith(input);
        reader.readTiles();
    }

    static void testFromFile(String file,String a, String h, long minTotalTimeout) {
        String[] args = String.format("-f %s -a %s -h %s", file, a, h).split(" ");
        runTest(1, minTotalTimeout, args);
    }

    static void testRandom(int times, int n, String a, String h, long minTotalTimeout) {
        String[] args = String.format("-r %d -a %s -h %s", n, a, h).split(" ");
        runTest(times, minTotalTimeout, args);
    }

    private static void runTest(int times, long minTotalTimeout, String... args) {
        List<Npuzzle> puzzles = new ArrayList<>();
        List<Executable> list = new ArrayList<>();

        for (int i = 0; i < times; i++) puzzles.add(Npuzzle.create(new Input(args)));

        ExecutorService service = Executors.newFixedThreadPool(times);
        try {
            for (Future<Output> future : service.invokeAll(puzzles, minTotalTimeout, TimeUnit.MINUTES)) {
                Output output = future.get();
                int n = output.getInput().getN();
                State received = output.getFinal();
                State expected = finals.get(n);
                list.add(() -> assertFalse(n == 0));
                list.add(() -> assertEquals(expected, received, wrongFinal(output.getInput(), output)));
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        } catch (CancellationException e) {
            System.err.printf("Killed: took too long.%n%n");
            System.err.printf("Puzzles were%n%s%n", puzzles);
            for (Npuzzle p : puzzles) logToJson(p, "Timeout");
            fail("Killed: took too long");
        } finally {
            service.shutdown();
        }
        Assertions.assertAll(list);
    }

    private static Supplier<String> wrongFinal(Input input, Output output) {
        logToJson(input, output, "WrongFinal");
        return () -> "WRONG FINAL";
    }

    private static void logToJson(Npuzzle puzzle, String reason) {
        logToJson(puzzle.input(), puzzle.output(), reason);
    }

    private static void logToJson(Input input, Output output, String reason) {
        if (input != null)
            if (output == null || !output.getFinal().equals(State.createFinal(input.getN()))) {
                JSONObject o = createLogJson(input, reason);
                Writer.writeJson(o.toString(), "src/test/resources/failed.json");
            }
    }

    private static JSONObject createLogJson(Input input, String reason) {
        JSONObject o = new JSONObject();
        return o.put("a", input.getAlgorithm())
                .put("n", input.getN())
                .put("h", input.getHeuristic())
                .put("initial", input.getInitialState())
                .put("message", reason);
    }

}
