package npuzzle;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static npuzzle.TestUtils.testFromFile;
import static npuzzle.TestUtils.testRandom;
import static npuzzle.utils.Constants.*;

class ThreesTest {
	@Disabled("greedy not implemented")
	@Test void manhattanGreedy() {
		testRandom(3,3, GREEDY, MANHATTAN, 1);
	}

	@Test void manhattanUniform() {
		testRandom(9,3, UNIFORM, MANHATTAN, 1);
	}

	@Test void manhattanAstar() {
		testRandom(3,3, ASTAR, MANHATTAN, 1);
	}

	@Disabled("greedy not implemented")
	@Test void euclideanGreedy() {
		testRandom(3,3, GREEDY, EUCLIDEAN, 1);
	}

	@Test void euclideanUniform() {
		testRandom(3,3, UNIFORM, EUCLIDEAN, 5);
	}

	@Test void euclideanAstar() {
		testRandom(3,3, ASTAR, EUCLIDEAN, 1);
	}

	@Disabled("greedy not implemented")
	@Test void hammingGreedy() {
		testRandom(3,3, GREEDY, HAMMING, 5);
	}

	@Test void hammingUniform() {
		testRandom(3,3, UNIFORM, HAMMING, 5);
	}

	@Test void hammingAstar() {
		testRandom(3,3, ASTAR, HAMMING, 5);
	}

	@Disabled("greedy not implemented")
	@Test void manhattanGreedyFile() {
		testFromFile("src/test/resources/testCases/threes/simple.txt", GREEDY, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/threes/medium.txt", GREEDY, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/threes/hard.txt", GREEDY, MANHATTAN, 1);
	}

	@Test void manhattanUniformFile() {
		testFromFile("src/test/resources/testCases/threes/simple.txt", UNIFORM, MANHATTAN, 1);
		testFromFile("src/test/resources/testCases/threes/medium.txt", UNIFORM, MANHATTAN, 2);
		testFromFile("src/test/resources/testCases/threes/hard.txt", UNIFORM, MANHATTAN, 4);
	}

	@Test void manhattanAstarFile() {
		testFromFile("src/test/resources/testCases/threes/simple.txt", ASTAR, MANHATTAN, 1);
//		testFromFile("src/test/resources/testCases/threes/medium.txt", ASTAR, MANHATTAN, 1);
//		testFromFile("src/test/resources/testCases/threes/hard.txt", ASTAR, MANHATTAN, 4);
	}

	@Disabled("greedy not implemented")
	@Test void euclideanGreedyFile() {
		testFromFile("src/test/resources/testCases/threes/simple.txt", GREEDY, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/threes/medium.txt", GREEDY, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/threes/hard.txt", GREEDY, EUCLIDEAN, 1);
	}

	@Test void euclideanUniformFile() {
		testFromFile("src/test/resources/testCases/threes/simple.txt", UNIFORM, EUCLIDEAN, 2);
		testFromFile("src/test/resources/testCases/threes/medium.txt", UNIFORM, EUCLIDEAN, 4);
		testFromFile("src/test/resources/testCases/threes/hard.txt", UNIFORM, EUCLIDEAN, 6);
	}

	@Test void euclideanAstarFile() {
		testFromFile("src/test/resources/testCases/threes/simple.txt", ASTAR, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/threes/medium.txt", ASTAR, EUCLIDEAN, 1);
		testFromFile("src/test/resources/testCases/threes/hard.txt", ASTAR, EUCLIDEAN, 1);
	}

	@Disabled("greedy not implemented")
	@Test void hammingGreedyFile() {
		testFromFile("src/test/resources/testCases/threes/simple.txt", GREEDY, HAMMING, 1);
		testFromFile("src/test/resources/testCases/threes/medium.txt", GREEDY, HAMMING, 1);
		testFromFile("src/test/resources/testCases/threes/hard.txt", GREEDY, HAMMING, 1);
	}

	@Test void hammingUniformFile() {
		testFromFile("src/test/resources/testCases/threes/simple.txt", UNIFORM, HAMMING, 4);
		testFromFile("src/test/resources/testCases/threes/medium.txt", UNIFORM, HAMMING, 6);
		testFromFile("src/test/resources/testCases/threes/hard.txt", UNIFORM, HAMMING, 8);
	}

	@Test void hammingAstarFile() {
		testFromFile("src/test/resources/testCases/threes/simple.txt", ASTAR, HAMMING, 1);
		testFromFile("src/test/resources/testCases/threes/medium.txt", ASTAR, HAMMING, 1);
		testFromFile("src/test/resources/testCases/threes/hard.txt", ASTAR, HAMMING, 1);
	}

}
