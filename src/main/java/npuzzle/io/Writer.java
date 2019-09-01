package npuzzle.io;

import npuzzle.logic.State;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

/**
 * @author dpozinen
 * <p>
 * used to write program output
 * public methods are synchronized to avoid multiple threads printing their output at the same time
 */

// TODO: refactor
public class Writer {

	public synchronized static void write(Input input, Output output, boolean fast, boolean toFile) {
		write(input, output, fast, Integer.toString(input.hashCode()));
	}

	public synchronized static void write(Input input, Output output, boolean fast, String filename) {
		if (Objects.isNull(filename)) {
			System.out.println(input);
			write(output.getPath(), fast, null);
			System.out.println(output);
		} else
			try {
				Path p = Paths.get(filename);
				Files.write(p, (input.toString() + "\n").getBytes());
				write(output.getPath(), fast, filename);
				Files.write(p, output.toString().getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public synchronized static void write(Input input, Output output, boolean fast) {
		write(input, output, fast, null);
	}

	public synchronized static void write(Input input, Output output) {
		write(input, output, true);
	}

	public synchronized static void write(List<State> states, boolean fast, String filename)
	{
		try {
			if (fast)
				writeFast(states, filename);
			else
				writeSlow(states, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public synchronized static void write(State state) {
		System.out.println(createPrettyTiles(state));
	}

	private static void writeFast(List<State> states, String fileName) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (State s : states)
			sb.append(s).append("\n");
		if (Objects.nonNull(fileName))
			Files.write(Paths.get(fileName), sb.toString().getBytes(), StandardOpenOption.APPEND);
		else
			System.out.println(sb);
	}

	// TODO: count proper offset
	private static void writeSlow(List<State> states, String filename) throws IOException {
		int i = states.size();
		StringBuilder sb = new StringBuilder();

		for (State state : states) {
			sb.append(createPrettyTiles(state));
			if (--i > 0)
				sb.append("\n\n");
		}

		if (Objects.isNull(filename))
			System.out.println(sb);
		else
			Files.write(Paths.get(filename), sb.toString().getBytes(), StandardOpenOption.APPEND);
	}

	private static StringBuilder createPrettyTiles(State state) {
		int col = 0;
		StringBuilder rows = new StringBuilder();

		for (Integer tile : state.getTiles()) {
			rows.append(String.format("%5s", String.valueOf(tile)));
			if (++col < state.getN()) {
				rows.append(" ");
			} else {
				rows.append("\n");
				col = 0;
			}
		}
		return rows;
	}

	public synchronized static void write(String s, String filename) {
		try {
			Path path = Paths.get(filename);
			if (Files.notExists(path)) Files.createFile(path);
			Files.write(path, s.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
