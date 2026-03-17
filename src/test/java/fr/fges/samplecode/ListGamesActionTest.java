package fr.fges.samplecode;

import fr.fges.ui.ListGamesAction;
import fr.fges.model.BoardGame;
import fr.fges.ui.GamePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListGamesActionTest {

    private GameService service;
    private GamePrinter printer;
    private ListGamesAction action;

    @BeforeEach
    void setUp() {
        service = mock(GameService.class);
        printer = mock(GamePrinter.class);
        action = new ListGamesAction(service, printer);
    }

    @Test
    void getLabel_shouldReturnCorrectLabel() {
        // Act
        String label = action.getLabel();

        // Assert
        assertEquals("List All Board Games", label);
    }

    @Test
    void execute_shouldDelegatePrintingToGamePrinter() {
        // Arrange
        when(service.getAllGames()).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy")
        ));

        // Act
        action.execute();

        // Assert
        verify(printer, times(1)).printGames(anyList());
    }

    @Test
    void execute_shouldPassAllGamesToPrinter() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("Pandemic", 2, 4, "coop")
        );
        when(service.getAllGames()).thenReturn(games);

        // Act
        action.execute();

        // Assert
        verify(printer).printGames(games);
    }
}