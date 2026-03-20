package fr.fges.samplecode;

import fr.fges.ui.ExitAction;
import fr.fges.ui.MenuEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExitActionTest {

    private ExitAction action;

    @BeforeEach
    void setUp() {
        action = new ExitAction();
    }

    // ── getLabel

    @Test
    void getLabel_shouldReturnExit() {
        // Arrange — action créée dans setUp()

        // Act
        String label = action.getLabel();

        // Assert
        assertEquals("Exit", label);
    }

    // ── implémentation de l'interface

    @Test
    void exitAction_shouldImplementMenuEntry() {
        // Assert
        assertInstanceOf(MenuEntry.class, action);
    }
}