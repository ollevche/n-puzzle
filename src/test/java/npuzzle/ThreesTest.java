package npuzzle;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static npuzzle.TestUtils.testRandom;
import static npuzzle.utils.Constants.*;

class ThreesTest {
	@Disabled("greedy not implemented")
	@Test void threesManhattanGreedy() {
		testRandom(3,3, GREEDY, MANHATTAN, 1);
	}

	@Test void threesManhattanUniform() {
		testRandom(9,3, UNIFORM, MANHATTAN, 1);
	}

	@Test void threesManhattanAstar() {
		testRandom(3,3, ASTAR, MANHATTAN, 1);
	}

	@Disabled("greedy not implemented")
	@Test void threesEuclideanGreedy() {
		testRandom(3,3, GREEDY, EUCLIDEAN, 1);
	}

	@Test void threesEuclideanUniform() {
		testRandom(3,3, UNIFORM, EUCLIDEAN, 5);
	}

	@Test void threesEuclideanAstar() {
		testRandom(3,3, ASTAR, EUCLIDEAN, 1);
	}

	@Disabled("greedy not implemented")
	@Test void threesHammingGreedy() {
		testRandom(3,3, GREEDY, HAMMING, 5);
	}

	@Test void threesHammingUniform() {
		testRandom(3,3, UNIFORM, HAMMING, 5);
	}

	@Test void threesHammingAstar() {
		testRandom(3,3, ASTAR, HAMMING, 5);
	}

}
