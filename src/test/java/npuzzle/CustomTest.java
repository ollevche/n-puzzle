package npuzzle;

import npuzzle.utils.Constants;
import org.junit.jupiter.api.Test;

class CustomTest {

	@Test void customTest() {
        TestUtils.testFromFile("src/test/resources/testCases/validation/lineCommentSequence.txt",
                Constants.UNIFORM, Constants.MANHATTAN, 1);
	}

}
