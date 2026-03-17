package fr.fges.samplecode;

import fr.fges.ui.AddGameAction;
import fr.fges.businesslogic.ActionHistory;
import fr.fges.model.BoardGame;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddGameActionTest {

    private GameService service;
    private UserInput input;
    private ActionHistory history;
    private AddGameAction action;

    @BeforeEach
    void setUp() {
        service = mock(GameService.class);
        input = mock(UserInput.class);
        history = new ActionHistory();
        action = new AddGameAction(service, input, history);
    }

    @Test
    void getLabel_shouldReturnCorrectLabel() {
        // Act
        String label = action.getLabel();

        // Assert
        assertEquals("Add Board Game", label);
    }

    @Test
    void execute_shouldCallAddGameOnService() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");

        // Act
        action.execute();

        // Assert
        verify(service, times(1)).addGame(any(BoardGame.class));
    }

    @Test
    void execute_shouldPushActionToHistory() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");

        // Act
        action.execute();

        // Assert
        assertEquals(1, history.size());
    }

    @Test
    void undo_shouldCallRemoveGameOnService() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");
        action.execute();

        // Act
        action.undo();

        // Assert
        verify(service, times(1)).removeGame(any(BoardGame.class));
    }

    @Test
    void undo_shouldRemoveTheCorrectGame() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");
        action.execute();

        // Act
        action.undo();

        // Assert
        verify(service).removeGame(argThat(g -> g.getTitle().equals("Catan")));
    }
}