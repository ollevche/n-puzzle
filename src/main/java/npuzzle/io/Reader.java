package npuzzle.io;

import static npuzzle.utils.Constants.*;

import java.io.*;

import org.apache.commons.cli.*;

import npuzzle.utils.InvalidInputException;

/**
 * @author dpozinen
 * @author ollevche
 * <p>
 * used to read program input
 */

public class Reader {

	private static Options options = prepareOptions();

	private Reader() {
	}

	private static Options prepareOptions() {
		Options options = new Options();

		options.addRequiredOption("a", ALGORITHM, true, ALGORITHM_DESCRIPTION);
		options.addRequiredOption("h", HEURISTIC, true, HEURISTIC_DESCRIPTION);
		options.addOption("f", FILE, true, FILE_DESCRIPTION);
		options.addOption("r", RANDOM, true, RANDOM_DESCRIPTION);

		return options;
	}

	public static boolean readInput(String[] args) {
		try {
			parseArgs(args);
			if (!Input.getInstance().isRandom())
				readTiles();
			return true;
		} catch (IOException e) {
			System.err.println("Cannot read input: " + e.getMessage());
		} catch (ParseException e) {
			System.err.println("Invalid argument: " + e.getMessage());
			new HelpFormatter().printHelp("N-puzzle", options);
		} catch (InvalidInputException e) {
			System.err.println(e.getMessage());
		}

		return false;
	}

	private static void parseArgs(String[] args) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(options, args);
		Validator validator = new Validator();

		validator.saveValidAlgorithm(line.getOptionValue(ALGORITHM));
		validator.saveValidHeuristic(line.getOptionValue(HEURISTIC));
		validator.saveValidFile(line.getOptionValue(FILE));
		if (line.hasOption(RANDOM))
			validator.saveValidRandomArg(line.getOptionValue(RANDOM));
	}

	private static void readTiles() throws IOException {
		String line;
		InputStream inputStream;
		Validator validator = new Validator();

		if (Input.getInstance().hasFile())
			inputStream = new BufferedInputStream(new FileInputStream(Input.getInstance().getFile()));
		else
			inputStream = System.in;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			while ((line = br.readLine()) != null)
				validator.validateLine(line);
		}
		validator.checkEnoughTiles();
		validator.saveValidatedTiles();
	}

}
