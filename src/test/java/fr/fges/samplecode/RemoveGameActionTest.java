package fr.fges.samplecode;

import fr.fges.businesslogic.ActionHistory;
import fr.fges.model.BoardGame;
import fr.fges.service.GameAdder;
import fr.fges.service.GameRemover;
import fr.fges.service.GameRepository;
import fr.fges.ui.RemoveGameAction;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveGameActionTest {

    private GameRepository repository;
    private GameRemover remover;
    private GameAdder adder;
    private UserInput input;
    private ActionHistory history;
    private RemoveGameAction action;

    @BeforeEach
    void setUp() {
        repository = mock(GameRepository.class);
        remover = mock(GameRemover.class);
        adder = mock(GameAdder.class);
        input = mock(UserInput.class);
        history = new ActionHistory();
        action = new RemoveGameAction(repository, remover, adder, input, history);
    }

    // ── getLabel

    @Test
    void getLabel_shouldReturnCorrectLabel() {
        // Arrange — action créée dans setUp()

        // Act
        String label = action.getLabel();

        // Assert
        assertEquals("Remove Board Game", label);
    }

    // ── execute

    @Test
    void execute_shouldCallRemoveGame_onRemover() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        when(repository.findAll()).thenReturn(List.of(game));
        when(input.getIntBetween("Select game number to remove", 1, 1)).thenReturn(1);

        // Act
        action.execute();

        // Assert
        verify(remover, times(1)).removeGame(game);
    }

    @Test
    void execute_shouldRemoveTheSelectedGame() {
        // Arrange
        BoardGame catan = new BoardGame("Catan", 3, 4, "strategy");
        BoardGame pandemic = new BoardGame("Pandemic", 2, 4, "coop");
        when(repository.findAll()).thenReturn(List.of(catan, pandemic));
        when(input.getIntBetween("Select game number to remove", 1, 2)).thenReturn(2);

        // Act
        action.execute();

        // Assert
        verify(remover).removeGame(pandemic);
    }

    @Test
    void execute_shouldPushActionToHistory() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        when(repository.findAll()).thenReturn(List.of(game));
        when(input.getIntBetween("Select game number to remove", 1, 1)).thenReturn(1);

        // Act
        action.execute();

        // Assert
        assertEquals(1, history.size());
    }

    @Test
    void execute_shouldNotRemoveNorPushHistory_whenCollectionIsEmpty() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        action.execute();

        // Assert
        verify(remover, never()).removeGame(any());
        assertEquals(0, history.size());
    }

    // ── undo

    @Test
    void undo_shouldCallAddGame_onAdder_withRemovedGame() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        when(repository.findAll()).thenReturn(List.of(game));
        when(input.getIntBetween("Select game number to remove", 1, 1)).thenReturn(1);
        action.execute();

        // Act
        action.undo();

        // Assert
        verify(adder, times(1)).addGame(game);
    }

    @Test
    void undo_shouldNotCallAdder_ifExecuteWasNeverCalled() {
        // Arrange — execute() jamais appelé, lastRemoved est null

        // Act
        action.undo();

        // Assert
        verify(adder, never()).addGame(any());
    }
}