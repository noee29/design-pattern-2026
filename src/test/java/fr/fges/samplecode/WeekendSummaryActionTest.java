package fr.fges.samplecode;

import fr.fges.action.WeekendSummaryAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void execute_shouldPrintThreeGamesOrLess() {
        // Arrange
        when(service.getAllGames()).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("7 Wonders", 3, 7, "strategy"),
                new BoardGame("Bingo", 2, 6, "family")
        ));

        // Act
        action.execute();

        // Assert passes if no exception thrown
    }
}