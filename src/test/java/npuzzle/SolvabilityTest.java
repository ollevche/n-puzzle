package npuzzle;

import npuzzle.utils.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SolvabilityTest {

    @Test
    void threeUnsolvable() {
        Assertions.assertThrows(InvalidInputException.class, () -> TestUtils.testSolvability("src/test/resources/testCases/validation/unsolvable/three.txt"));
    }

    @Test
    void fourUnsolvable() {
        Assertions.assertThrows(InvalidInputException.class, () -> TestUtils.testSolvability("src/test/resources/testCases/validation/unsolvable/four.txt"));
    }

    @Test
    void fiveUnsolvable() {
        Assertions.assertThrows(InvalidInputException.class, () -> TestUtils.testSolvability("src/test/resources/testCases/validation/unsolvable/five.txt"));
    }

    @Test
    void threeSolvable() {
        Assertions.assertDoesNotThrow(() -> TestUtils.testSolvability("src/test/resources/testCases/validation/solvable/three.txt"));
    }

    @Test
    void fourSolvable() {
        Assertions.assertDoesNotThrow(() -> TestUtils.testSolvability("src/test/resources/testCases/validation/solvable/four.txt"));
    }

    @Test
    void fiveSolvable() {
        Assertions.assertDoesNotThrow(() -> TestUtils.testSolvability("src/test/resources/testCases/validation/solvable/five.txt"));
    }

}
