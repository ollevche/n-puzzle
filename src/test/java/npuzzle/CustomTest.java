package npuzzle;

import npuzzle.utils.Constants;
import org.junit.jupiter.api.Test;

class CustomTest {

	@Test void customTest() {
        TestUtils.testFromFile("src/test/resources/testCases/fours/medium.txt",
                Constants.ASTAR, Constants.MANHATTAN, 1);
	}

}
