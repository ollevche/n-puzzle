package npuzzle.io;

import npuzzle.logic.State;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

/**
 * @author dpozinen
 * @author ollevche
 * <p>
 * used to write program output
 * public methods are synchronized to avoid multiple threads printing their input at the same time
 */

public class Writer {

	public synchronized static void write(List<State> states, boolean fast) {
		write(states, fast, null);
		System.out.println("Path length:" + states.size());
	}

	public synchronized static void write(String s) {
		System.out.println(s);
	}

	public synchronized static void write(List<State> states, boolean fast, String filename) {
		try {
			if (!Objects.isNull(filename) && !Files.exists(Paths.get(filename)))
				Files.createFile(Paths.get(filename));

			if (fast)
				writeFast(states, filename);
			else
				writeSlow(states, filename);

			if (!Objects.isNull(filename))
				Files.write(Paths.get(filename), ("Path length:" + states.size()).getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static void write(State state) {
		System.out.println(createPrettyTiles(state));
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

	private static void writeFast(List<State> states, String fileName) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (State s : states)
			sb.append(s).append("\n");
		if (Objects.nonNull(fileName))
			Files.write(Paths.get(fileName), sb.toString().getBytes());
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
			Files.write(Paths.get(filename), sb.toString().getBytes());
	}
}
