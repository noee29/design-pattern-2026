package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.policy.WeekendSummaryAction;
import fr.fges.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeekendSummaryActionTest {

    private GameService service;
    private WeekendSummaryAction action;

    @BeforeEach
    void setUp() {
        service = mock(GameService.class);
        action = new WeekendSummaryAction(service);
    }

    @Test
    void getLabel_shouldReturnCorrectLabel() {
        // Act
        String label = action.getLabel();

        // Assert
        assertEquals("View Summary (Weekend Special!)", label);
    }

    @Test
    void execute_shouldNotThrow_whenCollectionHasMoreThanThreeGames() {
        // Arrange
        when(service.getAllGames()).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("7 Wonders", 3, 7, "strategy"),
                new BoardGame("Bingo", 2, 6, "family"),
                new BoardGame("Pandemic", 2, 4, "coop")
        ));

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldNotThrow_whenCollectionHasThreeGamesOrLess() {
        // Arrange
        when(service.getAllGames()).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("7 Wonders", 3, 7, "strategy")
        ));

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
}