package npuzzle.io;

import npuzzle.utils.InvalidInputException;
import org.apache.commons.cli.*;

import java.io.*;

/**
 * @author dpozinen
 * <p>
 * used to read program input
 */

public class Reader {

	private static Options options = prepareOptions();

	private static final String ALGORITHM = "algorithm";
	private static final String HEURISTIC = "heuristic";
	private static final String FILE = "file";
	private static final String RANDOM = "random";

	// TODO: better descriptions
	private static final String ALGORITHM_DESCRIPTION = "Algorithm type to use.";
	private static final String HEURISTIC_DESCRIPTION = "Heuristic function to use.";
	private static final String FILE_DESCRIPTION = "File to use as source of input.";
	private static final String RANDOM_DESCRIPTION = "Use random input.";

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

		boolean isSuccessful = false;

		try {
			parseArgs(args);
			if (!Input.getInstance().isRandom())
				readTiles();
			isSuccessful = true;
		} catch (IOException e) {
			System.err.println("Cannot read input: " + e.getMessage());
		} catch (ParseException e) {
			System.err.println("Invalid argument: " + e.getMessage());
			new HelpFormatter().printHelp("N-puzzle", options);
		} catch (InvalidInputException e) {
			System.err.println(e.getMessage());
		}

		return isSuccessful;
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
