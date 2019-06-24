package npuzzle;

import npuzzle.io.Input;
import npuzzle.io.Reader;
import npuzzle.logic.Npuzzle;

public class App {

	public static void main(String[] args) {

		if (!Reader.readInput(args))
			return;

		System.out.println(Input.getInstance().toString());

		if (!Npuzzle.Execute())
			return;

		System.out.println("Executed successfully.");
	}

}
