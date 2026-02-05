package fr.fges.samplecode;

import fr.fges.action.ExitAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ExitActionTest {

    @Test
    void execute_shouldExitApplication() {
        // Arrange
        ExitAction action = new ExitAction();

        // Act & Assert
        assertThrows(SecurityException.class, action::execute);
    }
}