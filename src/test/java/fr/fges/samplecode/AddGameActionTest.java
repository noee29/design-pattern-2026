package fr.fges.samplecode;

import fr.fges.businesslogic.ActionHistory;
import fr.fges.model.BoardGame;
import fr.fges.service.GameAdder;
import fr.fges.service.GameRemover;
import fr.fges.ui.AddGameAction;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddGameActionTest {

    private GameAdder adder;
    private GameRemover remover;
    private UserInput input;
    private ActionHistory history;
    private AddGameAction action;

    @BeforeEach
    void setUp() {
        adder = mock(GameAdder.class);
        remover = mock(GameRemover.class);
        input = mock(UserInput.class);
        history = new ActionHistory();
        action = new AddGameAction(adder, remover, input, history);
    }

    // ── getLabel

    @Test
    void getLabel_shouldReturnCorrectLabel() {
        // Arrange — action créée dans setUp()

        // Act
        String label = action.getLabel();

        // Assert
        assertEquals("Add Board Game", label);
    }

    // ── execute

    @Test
    void execute_shouldCallAddGame_onAdder() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");

        // Act
        action.execute();

        // Assert
        verify(adder, times(1)).addGame(any(BoardGame.class));
    }

    @Test
    void execute_shouldAddGameWithCorrectTitle() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");

        // Act
        action.execute();

        // Assert
        verify(adder).addGame(argThat(g -> g.getTitle().equals("Catan")));
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
    void execute_shouldPushItselfToHistory() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");

        // Act
        action.execute();

        // Assert
        assertSame(action, history.pop());
    }

    // ── undo

    @Test
    void undo_shouldCallRemoveGame_onRemover_afterExecute() {
        // Arrange
        when(input.getString("Title: ")).thenReturn("Catan");
        when(input.getInt("Minimum Players: ")).thenReturn(3);
        when(input.getInt("Maximum Players: ")).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, strategy): ")).thenReturn("strategy");
        action.execute();

        // Act
        action.undo();

        // Assert
        verify(remover, times(1)).removeGame(any(BoardGame.class));
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
        verify(remover).removeGame(argThat(g -> g.getTitle().equals("Catan")));
    }

    @Test
    void undo_shouldNotCallRemover_ifExecuteWasNeverCalled() {
        // Arrange — execute() jamais appelé, lastAdded est null

        // Act
        action.undo();

        // Assert
        verify(remover, never()).removeGame(any());
    }
}