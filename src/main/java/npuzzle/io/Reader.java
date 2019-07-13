package npuzzle.io;

import npuzzle.utils.InvalidInputException;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static npuzzle.utils.Constants.*;

/**
 * @author dpozinen
 * @author ollevche
 * <p>
 * used to read program input
 */

public class Reader {

	private final Input input;
	private final Validator validator;
	private static Options options = prepareOptions();

	private Reader(Input input) {
		this.input = input;
		validator = Validator.create(input);
	}

	public static Reader createWith(Input input) {
		return new Reader(input);
	}

	private static Options prepareOptions() {
		Options options = new Options();

		options.addRequiredOption("a", ALGORITHM, true, ALGORITHM_DESCRIPTION);
		options.addRequiredOption("h", HEURISTIC, true, HEURISTIC_DESCRIPTION);
		options.addOption("f", FILE, true, FILE_DESCRIPTION);
		options.addOption("r", RANDOM, true, RANDOM_DESCRIPTION);

		return options;
	}

	public boolean fillInput() {
		try {
			parseArgs(input.getArgs());
			if (!input.isRandom())
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

	private void parseArgs(String[] args) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(options, args);

		validator.saveValidAlgorithm(line.getOptionValue(ALGORITHM));
		validator.saveValidHeuristic(line.getOptionValue(HEURISTIC));
		validator.saveValidFile(line.getOptionValue(FILE));
		if (line.hasOption(RANDOM))
			validator.saveValidRandomArg(line.getOptionValue(RANDOM));
	}

	private void readTiles() throws IOException {
		String line;
		InputStream inputStream;

		if (input.hasFile())
			inputStream = new BufferedInputStream(new FileInputStream(input.getFile()));
		else
			inputStream = System.in;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			while ((line = br.readLine()) != null)
				validator.validateLine(line);
		}
		validator.checkEnoughTiles();
		validator.saveValidatedTiles(input);
	}

	public static List<Input> splitArgs(String[] args) {
		List<Input> inputList = new ArrayList<>();
		List<String> argParts = List.of(StringUtils.join(args, " ").split("\\|"));

		for (String arg : argParts)
			inputList.add(Input.fromArgs(arg.trim().split(" ")));

		return inputList;
	}

}
