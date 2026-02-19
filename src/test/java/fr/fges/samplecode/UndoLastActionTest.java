package fr.fges.samplecode;

import fr.fges.action.AddGameAction;
import fr.fges.businesslogic.ActionHistory;
import fr.fges.businesslogic.UndoLastAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UndoLastActionTest {

    private GameService service;
    private UserInput input;
    private ActionHistory history;
    private AddGameAction addAction;
    private UndoLastAction undoAction;

    @BeforeEach
    void setUp() {
        service = mock(GameService.class);
        input = mock(UserInput.class);
        history = new ActionHistory();
        addAction = new AddGameAction(service, input, history);
        undoAction = new UndoLastAction(history);
    }

    @Test
    void execute_shouldUndoLastAction() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");
        addAction.execute();

        // Act
        undoAction.execute();

        // Assert
        verify(service, times(1)).removeGame(any(BoardGame.class));
        assertTrue(history.isEmpty());
    }

    @Test
    void execute_whenHistoryEmpty_shouldPrintNothingToUndo() {
        // Act & Assert — aucune exception ne doit être levée
        assertDoesNotThrow(undoAction::execute);
    }
}