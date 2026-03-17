package fr.fges.samplecode;

import fr.fges.ui.RecommendGameAction;
import fr.fges.model.BoardGame;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendGameActionTest {

    private GameService service;
    private UserInput input;
    private RecommendGameAction action;

    @BeforeEach
    void setUp() {
        service = mock(GameService.class);
        input = mock(UserInput.class);
        action = new RecommendGameAction(service, input);
    }

    @Test
    void getLabel_shouldReturnCorrectLabel() {
        // Act
        String label = action.getLabel();

        // Assert
        assertEquals("Recommend Game", label);
    }

    @Test
    void execute_shouldNotThrow_whenCompatibleGameExists() {
        // Arrange
        when(service.getAllGames()).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("7 Wonders", 3, 7, "strategy")
        ));
        when(input.getIntAtLeast("How many players?", 1)).thenReturn(4);

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldNotThrow_whenCollectionIsEmpty() {
        // Arrange
        when(service.getAllGames()).thenReturn(List.of());

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldNotThrow_whenNoCompatibleGame() {
        // Arrange
        when(service.getAllGames()).thenReturn(List.of(
                new BoardGame("SoloGame", 1, 1, "solo")
        ));
        when(input.getIntAtLeast("How many players?", 1)).thenReturn(5);

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }
}