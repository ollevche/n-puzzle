package npuzzle;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static npuzzle.TestUtils.testFromFile;
import static npuzzle.TestUtils.testRandom;
import static npuzzle.utils.Constants.*;

class FoursTest {


	@Test void manhattanGreedy() {
		testRandom(3,4, GREEDY, MANHATTAN, 1);
	}

	@Test void manhattanUniform() {
		testRandom(3,4, UNIFORM, MANHATTAN, 5);
	}

	@Test void manhattanAstar() {
		testRandom(1,4, ASTAR, MANHATTAN, 5);
	}


	@Test void euclideanGreedy() {
		testRandom(3,4, GREEDY, EUCLIDEAN, 1);
	}

	@Test void euclideanUniform() {
		testRandom(3,4, UNIFORM, EUCLIDEAN, 7);
	}

	@Test void euclideanAstar() {
		testRandom(3,4, ASTAR, EUCLIDEAN, 5);
	}


	@Test void hammingGreedy() {
		testRandom(3,4, GREEDY, HAMMING, 1);
	}

	@Test void hammingUniform() {
		testRandom(3,4, UNIFORM, HAMMING, 7);
	}

	@Test void hammingAstar() {
		testRandom(3,4, ASTAR, HAMMING, 5);
	}


	@Test void manhattanGreedyFile() {
		testFromFile("src/test/resources/testCases/fours/simple.txt", GREEDY, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/fours/medium.txt", GREEDY, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/fours/hard.txt", GREEDY, MANHATTAN, 1);
	}

	@Test void manhattanUniformFile() {
		testFromFile("src/test/resources/testCases/fours/simple.txt", UNIFORM, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/fours/medium.txt", UNIFORM, MANHATTAN, 2);
		testFromFile("src/test/resources/testCases/fours/hard.txt", UNIFORM, MANHATTAN, 4);
	}

	@Test void manhattanAstarFile() {
		testFromFile("src/test/resources/testCases/fours/simple.txt", ASTAR, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/fours/medium.txt", ASTAR, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/fours/hard.txt", ASTAR, MANHATTAN, 4);
	}


	@Test void euclideanGreedyFile() {
		testFromFile("src/test/resources/testCases/fours/simple.txt", GREEDY, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/fours/medium.txt", GREEDY, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/fours/hard.txt", GREEDY, EUCLIDEAN, 1);
	}

	@Test void euclideanUniformFile() {
		testFromFile("src/test/resources/testCases/fours/simple.txt", UNIFORM, EUCLIDEAN, 2);
		testFromFile("src/test/resources/testCases/fours/medium.txt", UNIFORM, EUCLIDEAN, 4);
		testFromFile("src/test/resources/testCases/fours/hard.txt", UNIFORM, EUCLIDEAN, 6);
	}

	@Test void euclideanAstarFile() {
		testFromFile("src/test/resources/testCases/fours/simple.txt", ASTAR, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/fours/medium.txt", ASTAR, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/fours/hard.txt", ASTAR, EUCLIDEAN, 1);
	}


	@Test void hammingGreedyFile() {
		testFromFile("src/test/resources/testCases/fours/simple.txt", GREEDY, HAMMING, 1);
		testFromFile("src/test/resources/testCases/fours/medium.txt", GREEDY, HAMMING, 1);
		testFromFile("src/test/resources/testCases/fours/hard.txt", GREEDY, HAMMING, 1);
	}

	@Test void hammingUniformFile() {
		testFromFile("src/test/resources/testCases/fours/simple.txt", UNIFORM, HAMMING, 4);
		testFromFile("src/test/resources/testCases/fours/medium.txt", UNIFORM, HAMMING, 6);
		testFromFile("src/test/resources/testCases/fours/hard.txt", UNIFORM, HAMMING, 8);
	}

	@Test void hammingAstarFile() {
		testFromFile("src/test/resources/testCases/fours/simple.txt", ASTAR, HAMMING, 1);
		testFromFile("src/test/resources/testCases/fours/medium.txt", ASTAR, HAMMING, 1);
		testFromFile("src/test/resources/testCases/fours/hard.txt", ASTAR, HAMMING, 1);
	}
}
