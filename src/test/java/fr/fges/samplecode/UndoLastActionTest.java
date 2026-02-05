package fr.fges.samplecode;

import fr.fges.action.AddGameAction;
import fr.fges.action.UndoLastAction;
import fr.fges.history.ActionHistory;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

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
        assert(history.isEmpty());
    }

    @Test
    void execute_whenHistoryEmpty_shouldPrintNothingToUndo() {
        // Act
        undoAction.execute(); // no actions in history
        // Assert passes if no exception thrown
    }
}