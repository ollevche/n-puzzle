package npuzzle;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static npuzzle.TestUtils.testRandom;
import static npuzzle.utils.Constants.*;

class FoursTest {

	@Disabled("greedy not implemented")
	@Test void foursManhattanGreedy() {
		testRandom(3,4, GREEDY, MANHATTAN, 1);
	}

	@Test void foursManhattanUniform() {
		testRandom(3,4, UNIFORM, MANHATTAN, 1);
	}

	@Test void foursManhattanAstar() {
		testRandom(3,4, ASTAR, MANHATTAN, 1);
	}

	@Disabled("greedy not implemented")
	@Test void foursEuclideanGreedy() {
		testRandom(3,4, GREEDY, EUCLIDEAN, 1);
	}

	@Test void foursEuclideanUniform() {
		testRandom(3,4, UNIFORM, EUCLIDEAN, 2);
	}

	@Test void foursEuclideanAstar() {
		testRandom(3,4, ASTAR, EUCLIDEAN, 1);
	}

	@Disabled("greedy not implemented")
	@Test void foursHammingGreedy() {
		testRandom(3,4, GREEDY, HAMMING, 1);
	}

	@Test void foursHammingUniform() {
		testRandom(3,4, UNIFORM, HAMMING, 2);
	}

	@Test void foursHammingAstar() {
		testRandom(3,4, ASTAR, HAMMING, 1);
	}

}
