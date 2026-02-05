package fr.fges.samplecode;

import fr.fges.action.WeekendSummaryAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class WeekendSummaryActionTest {

    @Test
    void execute_shouldGetAllGames() {
        // Arrange
        GameService service = mock(GameService.class);
        when(service.getAllGames()).thenReturn(List.of(
                new BoardGame("Uno", 2, 10, "Card")
        ));

        WeekendSummaryAction action = new WeekendSummaryAction(service);

        // Act
        action.execute();

        // Assert
        verify(service).getAllGames();
    }
}