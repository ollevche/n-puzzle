package npuzzle;

import npuzzle.utils.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationTest {

    @Test
    void empty() {
        assertThrows(InvalidInputException.class, () -> TestUtils.testValidation("src/test/resources/testCases/validation/empty.txt"));
    }

    @Test
    void largerValue() {
        assertThrows(InvalidInputException.class, () -> TestUtils.testValidation("src/test/resources/testCases/validation/largerValue.txt"));
    }

    @Test
    void lineCommentSequence() {
        Assertions.assertDoesNotThrow(() -> TestUtils.testValidation("src/test/resources/testCases/validation/lineCommentSequence.txt"));
    }

    @Test
    void missingTiles() {
        assertThrows(InvalidInputException.class, () -> TestUtils.testValidation("src/test/resources/testCases/validation/missingTiles.txt"));
    }

    @Test
    void nonNumeric() {
        assertThrows(InvalidInputException.class, () -> TestUtils.testValidation("src/test/resources/testCases/validation/nonNumeric.txt"));
    }

    @Test
    void nonsense() {
        assertThrows(InvalidInputException.class, () -> TestUtils.testValidation("src/test/resources/testCases/validation/nonsense.txt"));
    }

    @Test
    void noSize() {
        assertThrows(InvalidInputException.class, () -> TestUtils.testValidation("src/test/resources/testCases/validation/noSize.txt"));
    }

}
