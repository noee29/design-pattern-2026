package fr.fges.samplecode;

import fr.fges.action.AddGameAction;
import fr.fges.action.UndoableAction;
import fr.fges.history.ActionHistory;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ActionHistoryTest {

    private ActionHistory history;
    private GameService service;
    private UserInput input;

    @BeforeEach
    void setUp() {
        history = new ActionHistory();
        service = mock(GameService.class);
        input = mock(UserInput.class);
    }

    @Test
    void pushAndPop_shouldWorkCorrectly() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");

        UndoableAction action = new AddGameAction(service, input, history);

        // Act
        action.execute();
        UndoableAction popped = history.pop();

        // Assert
        assertEquals(action, popped);
        assertTrue(history.isEmpty());
    }

    @Test
    void isEmpty_shouldReturnTrueWhenEmpty() {
        assertTrue(history.isEmpty());
    }

    @Test
    void pop_shouldThrowExceptionWhenEmpty() {
        assertThrows(Exception.class, history::pop); // Stack pop throws EmptyStackException
    }
}