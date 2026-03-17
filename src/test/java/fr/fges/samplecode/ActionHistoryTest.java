package fr.fges.samplecode;

import fr.fges.ui.AddGameAction;
import fr.fges.businesslogic.ActionHistory;
import fr.fges.businesslogic.UndoableAction;
import fr.fges.model.BoardGame;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void isEmpty_shouldReturnTrue_whenHistoryIsEmpty() {
        // Act
        boolean result = history.isEmpty();

        // Assert
        assertTrue(result);
    }

    @Test
    void isEmpty_shouldReturnFalse_afterPush() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");
        UndoableAction<BoardGame> action = new AddGameAction(service, input, history);
        action.execute();

        // Act
        boolean result = history.isEmpty();

        // Assert
        assertFalse(result);
    }

    @Test
    void size_shouldReturnCorrectCount() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");
        UndoableAction<BoardGame> action = new AddGameAction(service, input, history);
        action.execute();

        // Act
        int size = history.size();

        // Assert
        assertEquals(1, size);
    }

    @Test
    void pop_shouldReturnLastPushedAction() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");
        UndoableAction<BoardGame> action = new AddGameAction(service, input, history);
        action.execute();

        // Act
        UndoableAction<BoardGame> popped = history.pop();

        // Assert
        assertEquals(action, popped);
        assertTrue(history.isEmpty());
    }

    @Test
    void pop_shouldThrowException_whenHistoryIsEmpty() {
        // Act & Assert
        assertThrows(Exception.class, history::pop);
    }
}