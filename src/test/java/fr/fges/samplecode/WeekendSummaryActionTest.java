package fr.fges.samplecode;

import fr.fges.policy.WeekendSummaryAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WeekendSummaryActionTest {

    private GameService service;
    private WeekendSummaryAction action;

    @BeforeEach
    void setUp() {
        service = mock(GameService.class);
        action = new WeekendSummaryAction(service);
    }

    @Test
    void execute_shouldPrintThreeRandomGames() {
        // Arrange
        when(service.getAllGames()).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("7 Wonders", 3, 7, "strategy"),
                new BoardGame("Bingo", 2, 6, "family")
        ));

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_whenEmptyCollection_shouldNotThrow() {
        // Arrange
        when(service.getAllGames()).thenReturn(List.of());

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }
}