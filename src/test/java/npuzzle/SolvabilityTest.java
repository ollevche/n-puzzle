package npuzzle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SolvabilityTest {

    @Test
    void three() {
        Assertions.assertDoesNotThrow(() -> TestUtils.testSolvability("src/test/resources/testCases/validation/unsolvable/three.txt"));
    }

    @Test
    void four() {
        Assertions.assertDoesNotThrow(() -> TestUtils.testSolvability("src/test/resources/testCases/validation/unsolvable/four.txt"));
    }

    @Test
    void five() {
        Assertions.assertDoesNotThrow(() -> TestUtils.testSolvability("src/test/resources/testCases/validation/unsolvable/five.txt"));
    }

}
