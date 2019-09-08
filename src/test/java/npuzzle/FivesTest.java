package npuzzle;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static npuzzle.TestUtils.testFromFile;
import static npuzzle.TestUtils.testRandom;
import static npuzzle.utils.Constants.*;

class FivesTest {

	@Disabled("greedy not implemented")
	@Test void manhattanGreedy() {
		testRandom(3,5, GREEDY, MANHATTAN, 15);
	}

	@Test void manhattanUniform() {
		testRandom(3,5, UNIFORM, MANHATTAN, 15);
	}

	@Test void manhattanAstar() {
		testRandom(1,5, ASTAR, MANHATTAN, 15);
	}

	@Disabled("greedy not implemented")
	@Test void euclideanGreedy() {
		testRandom(3,5, GREEDY, EUCLIDEAN, 15);
	}

	@Test void euclideanUniform() {
		testRandom(3,5, UNIFORM, EUCLIDEAN, 15);
	}

	@Test void euclideanAstar() {
		testRandom(3,5, ASTAR, EUCLIDEAN, 15);
	}

	@Disabled("greedy not implemented")
	@Test void hammingGreedy() {
		testRandom(3,5, GREEDY, HAMMING, 20);
	}

	@Test void hammingUniform() {
		testRandom(3,5, UNIFORM, HAMMING, 20);
	}

	@Test void hammingAstar() {
		testRandom(3,5, ASTAR, HAMMING, 20);
	}


	@Disabled("greedy not implemented")
	@Test void manhattanGreedyFile() {
		testFromFile("src/test/resources/testCases/fives/simple.txt", GREEDY, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/fives/medium.txt", GREEDY, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/fives/hard.txt", GREEDY, MANHATTAN, 1);
	}

	@Test void manhattanUniformFile() {
		testFromFile("src/test/resources/testCases/fives/simple.txt", UNIFORM, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/fives/medium.txt", UNIFORM, MANHATTAN, 2);
		testFromFile("src/test/resources/testCases/fives/hard.txt", UNIFORM, MANHATTAN, 4);
	}

	@Test void manhattanAstarFile() {
		testFromFile("src/test/resources/testCases/fives/simple.txt", ASTAR, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/fives/medium.txt", ASTAR, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/fives/hard.txt", ASTAR, MANHATTAN, 4);
	}

	@Disabled("greedy not implemented")
	@Test void euclideanGreedyFile() {
		testFromFile("src/test/resources/testCases/fives/simple.txt", GREEDY, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/fives/medium.txt", GREEDY, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/fives/hard.txt", GREEDY, EUCLIDEAN, 1);
	}

	@Test void euclideanUniformFile() {
		testFromFile("src/test/resources/testCases/fives/simple.txt", UNIFORM, EUCLIDEAN, 2);
		testFromFile("src/test/resources/testCases/fives/medium.txt", UNIFORM, EUCLIDEAN, 4);
		testFromFile("src/test/resources/testCases/fives/hard.txt", UNIFORM, EUCLIDEAN, 6);
	}

	@Test void euclideanAstarFile() {
		testFromFile("src/test/resources/testCases/fives/simple.txt", ASTAR, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/fives/medium.txt", ASTAR, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/fives/hard.txt", ASTAR, EUCLIDEAN, 1);
	}

	@Disabled("greedy not implemented")
	@Test void hammingGreedyFile() {
		testFromFile("src/test/resources/testCases/fives/simple.txt", GREEDY, HAMMING, 1);
		testFromFile("src/test/resources/testCases/fives/medium.txt", GREEDY, HAMMING, 1);
		testFromFile("src/test/resources/testCases/fives/hard.txt", GREEDY, HAMMING, 1);
	}

	@Test void hammingUniformFile() {
		testFromFile("src/test/resources/testCases/fives/simple.txt", UNIFORM, HAMMING, 4);
		testFromFile("src/test/resources/testCases/fives/medium.txt", UNIFORM, HAMMING, 6);
		testFromFile("src/test/resources/testCases/fives/hard.txt", UNIFORM, HAMMING, 8);
	}

	@Test void hammingAstarFile() {
		testFromFile("src/test/resources/testCases/fives/simple.txt", ASTAR, HAMMING, 1);
		testFromFile("src/test/resources/testCases/fives/medium.txt", ASTAR, HAMMING, 1);
		testFromFile("src/test/resources/testCases/fives/hard.txt", ASTAR, HAMMING, 1);
	}

}
