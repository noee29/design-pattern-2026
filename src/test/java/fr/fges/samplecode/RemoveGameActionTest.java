package fr.fges.samplecode;

import fr.fges.action.RemoveGameAction;
import fr.fges.businesslogic.ActionHistory;
import fr.fges.model.BoardGame;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveGameActionTest {

    private GameService service;
    private UserInput input;
    private ActionHistory history;
    private RemoveGameAction action;

    @BeforeEach
    void setUp() {
        service = mock(GameService.class);
        input = mock(UserInput.class);
        history = new ActionHistory();
        action = new RemoveGameAction(service, input, history);
    }

    @Test
    void getLabel_shouldReturnCorrectLabel() {
        // Act
        String label = action.getLabel();

        // Assert
        assertEquals("Remove Board Game", label);
    }

    @Test
    void execute_shouldCallRemoveGameOnService() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        when(service.getAllGames()).thenReturn(List.of(game));
        when(input.getIntBetween("Select game number to remove", 1, 1)).thenReturn(1);

        // Act
        action.execute();

        // Assert
        verify(service, times(1)).removeGame(game);
    }

    @Test
    void execute_shouldPushActionToHistory() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        when(service.getAllGames()).thenReturn(List.of(game));
        when(input.getIntBetween("Select game number to remove", 1, 1)).thenReturn(1);

        // Act
        action.execute();

        // Assert
        assertEquals(1, history.size());
    }

    @Test
    void execute_shouldNotRemove_whenCollectionIsEmpty() {
        // Arrange
        when(service.getAllGames()).thenReturn(List.of());

        // Act
        action.execute();

        // Assert
        verify(service, never()).removeGame(any());
    }

    @Test
    void undo_shouldCallAddGameOnService_withTheRemovedGame() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        when(service.getAllGames()).thenReturn(List.of(game));
        when(input.getIntBetween("Select game number to remove", 1, 1)).thenReturn(1);
        action.execute();

        // Act
        action.undo();

        // Assert
        verify(service, times(1)).addGame(game);
    }
}