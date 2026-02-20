package fr.fges.samplecode;

import fr.fges.action.ExitAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExitActionTest {

    @Test
    void getLabel_shouldReturnCorrectLabel() {
        // Arrange
        ExitAction action = new ExitAction();

        // Act
        String label = action.getLabel();

        // Assert
        assertEquals("Exit", label);
    }

    @Test
    void execute_shouldCallSystemExit() {
        // Arrange
        ExitAction action = new ExitAction();

        // Act & Assert
        assertThrows(SecurityException.class, action::execute);
    }
}