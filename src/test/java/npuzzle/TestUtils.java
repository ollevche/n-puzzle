package npuzzle;

import npuzzle.io.Output;
import npuzzle.io.Writer;
import npuzzle.logic.State;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

class TestUtils {

	static void logToJson(Output output, long timeExpected, TimeUnit unit) {
		JSONObject o = new JSONObject();
		o.put("a", output.getInput().getAlgorithm());
		o.put("h", output.getInput().getHeuristic());
		o.put("n", output.getInput().getN());
		o.put("initial", output.getInput().getInitialState());
		o.put("comment", String.format("Killed. Time elapsed: %s s. Time expected: %s",
							output.getStopwatch().elapsed(unit), timeExpected));
		Writer.write(o.toString() + ",", "/resources/failedTests.json");
	}

	void logToJson(State received, State expected) {

	}
}
