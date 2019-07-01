package npuzzle.io;

import java.util.List;

import npuzzle.logic.State;

/**
 * @author dpozinen
 * @author ollevche
 * <p>
 * used to write program output
 */

public class Writer {

	public static void write(List<State> states, boolean fast) {
		if (fast)
			writeFast(states);
		else
			writeSlow(states);
		System.out.println("Path length:" + states.size());
	}

	public static void write(State state) {
		StringBuilder rows = new StringBuilder();
		int col = 0;

		for (Integer tile : state.getTiles()) {
			rows.append(String.format("%5s", String.valueOf(tile)));
			if (++col < Input.getInstance().getN()) {
				rows.append(" ");
			} else {
				rows.append("\n");
				col = 0;
			}
		}
		System.out.println(rows);
	}

	private static void writeFast(List<State> states) {
		StringBuilder sb = new StringBuilder();
		for (State s : states)
			sb.append(s).append("\n");
		System.out.println(sb);
	}

	// TODO: count proper offset
	private static void writeSlow(List<State> states) {
		int i = states.size();
		int offset = 15;
		String format = "%" + offset + "s\n";
		String arrowFormat = "%" + (offset + 1) + "s\n";

		for (State state : states) {
			write(state);
			if (--i > 0) {
				System.out.printf(format, "|");
				System.out.printf(format, "|");
				System.out.printf(arrowFormat, "\\ /");
			}
		}
	}
}
