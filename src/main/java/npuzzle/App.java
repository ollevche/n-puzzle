package npuzzle;

import npuzzle.io.Reader;
import npuzzle.logic.Npuzzle;
import npuzzle.utils.Constants;

public class App {

	// TODO: proper main() function

	public static void main(String[] args) {
		Reader reader = new Reader();
//				new Reader("C:\\Users\\User\\Desktop\\Code\\Reports\\STATE.txt");
		reader.readArgs(args);

		new Npuzzle(Constants.GREEDY, reader.read()).execute();

	}

}
