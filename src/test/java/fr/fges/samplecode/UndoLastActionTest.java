package fr.fges.samplecode;

import fr.fges.businesslogic.ActionHistory;
import fr.fges.businesslogic.UndoLastAction;
import fr.fges.businesslogic.UndoableAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameAdder;
import fr.fges.service.GameRemover;
import fr.fges.ui.AddGameAction;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UndoLastActionTest {

    private ActionHistory history;
    private UndoLastAction undoAction;

    @BeforeEach
    void setUp() {
        history = new ActionHistory();
        undoAction = new UndoLastAction(history);
    }

    // ── getLabel

    @Test
    void getLabel_shouldReturnCorrectLabel() {
        // Arrange — action créée dans setUp()

        // Act
        String label = undoAction.getLabel();

        // Assert
        assertEquals("Undo Last Action", label);
    }

    // ── execute

    @Test
    void execute_shouldNotThrow_whenHistoryIsEmpty() {
        // Arrange — historique vide

        // Act & Assert
        assertDoesNotThrow(undoAction::execute);
    }

    @Test
    void execute_shouldCallUndo_onLastAction() {
        // Arrange
        UndoableAction<BoardGame> lastAction = mock(UndoableAction.class);
        history.push(lastAction);

        // Act
        undoAction.execute();

        // Assert
        verify(lastAction, times(1)).undo();
    }

    @Test
    void execute_shouldEmptyHistory_afterUndo() {
        // Arrange
        UndoableAction<BoardGame> lastAction = mock(UndoableAction.class);
        history.push(lastAction);

        // Act
        undoAction.execute();

        // Assert
        assertTrue(history.isEmpty());
    }

    @Test
    void execute_shouldOnlyUndoLastAction_whenMultipleActionsInHistory() {
        // Arrange
        UndoableAction<BoardGame> first = mock(UndoableAction.class);
        UndoableAction<BoardGame> second = mock(UndoableAction.class);
        history.push(first);
        history.push(second);

        // Act
        undoAction.execute();

        // Assert — seul le dernier doit être annulé
        verify(second, times(1)).undo();
        verify(first, never()).undo();
    }

    @Test
    void execute_withRealAddGameAction_shouldCallRemoverOnUndo() {
        // Arrange
        GameAdder adder = mock(GameAdder.class);
        GameRemover remover = mock(GameRemover.class);
        UserInput input = mock(UserInput.class);
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");

        AddGameAction addAction = new AddGameAction(adder, remover, input, history);
        addAction.execute(); // pousse dans l'historique

        // Act
        undoAction.execute();

        // Assert
        verify(remover, times(1)).removeGame(any(BoardGame.class));
    }
}