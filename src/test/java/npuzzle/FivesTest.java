package npuzzle;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static npuzzle.TestUtils.testRandom;
import static npuzzle.utils.Constants.*;

class FivesTest {

	@Disabled("greedy not implemented")
	@Test void foursManhattanGreedy() {
		testRandom(3,5, GREEDY, MANHATTAN, 15);
	}

	@Test void foursManhattanUniform() {
		testRandom(3,5, UNIFORM, MANHATTAN, 15);
	}

	@Test void foursManhattanAstar() {
		testRandom(3,5, ASTAR, MANHATTAN, 15);
	}

	@Disabled("greedy not implemented")
	@Test void foursEuclideanGreedy() {
		testRandom(3,5, GREEDY, EUCLIDEAN, 15);
	}

	@Test void foursEuclideanUniform() {
		testRandom(3,5, UNIFORM, EUCLIDEAN, 15);
	}

	@Test void foursEuclideanAstar() {
		testRandom(3,5, ASTAR, EUCLIDEAN, 15);
	}

	@Disabled("greedy not implemented")
	@Test void fivesHammingGreedy() {
		testRandom(3,5, GREEDY, HAMMING, 20);
	}

	@Test void fivesHammingUniform() {
		testRandom(3,5, UNIFORM, HAMMING, 20);
	}

	@Test void fivesHammingAstar() {
		testRandom(3,5, ASTAR, HAMMING, 20);
	}

}
