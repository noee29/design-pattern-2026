package fr.fges.samplecode;

import fr.fges.action.ListGamesAction;
import fr.fges.service.GameService;
import fr.fges.ui.GamePrinter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class ListGamesActionTest {

    @Test
    void execute_shouldPrintGames() {
        // Arrange
        GameService service = mock(GameService.class);
        GamePrinter printer = mock(GamePrinter.class);

        when(service.getAllGames()).thenReturn(List.of());

        ListGamesAction action = new ListGamesAction(service, printer);

        // Act
        action.execute();

        // Assert
        verify(printer).printGames(anyList());
    }
}