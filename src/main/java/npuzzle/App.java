package npuzzle;

import npuzzle.io.Input;
import npuzzle.io.Reader;
import npuzzle.logic.Npuzzle;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class App {

	public static void main(String[] args) {
		List<Input> inputList = Reader.splitArgs(args);
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(inputList.size());

		for (Input input : inputList) executor.submit(Npuzzle.create(input));
		executor.shutdown();
	}

}
