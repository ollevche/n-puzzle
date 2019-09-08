package npuzzle;

import npuzzle.utils.Constants;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class CustomTest {

	@Test void customTest() {
//        TestUtils.testFromFile("src/test/resources/testCases/fours/medium.txt",
//                Constants.ASTAR, Constants.MANHATTAN, 1);

		TestUtils.testManual(Arrays.asList(5, 15, 10, 12, 8, 4, 2, 6, 0, 7, 14, 3, 13, 9, 11, 1), Constants.ASTAR, Constants.MANHATTAN);
	}

}
