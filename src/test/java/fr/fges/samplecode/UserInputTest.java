package fr.fges.samplecode;

import fr.fges.UserInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UserInput class.
 */
class UserInputTest {

    @Test
    void userInput_shouldBeCreated() {
        // Arrange & Act
        UserInput input = new UserInput();

        // Assert
        assertNotNull(input);
    }
}
