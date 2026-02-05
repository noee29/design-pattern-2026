package fr.fges.samplecode;

import fr.fges.action.RemoveGameAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class RemoveGameActionTest {

    @Test
    void execute_shouldRemoveSelectedGame() {
        // Arrange
        GameService service = mock(GameService.class);
        UserInput input = mock(UserInput.class);

        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");
        when(service.getAllGames()).thenReturn(List.of(game));
        when(input.getIntBetween(anyString(), anyInt(), anyInt())).thenReturn(1);

        RemoveGameAction action = new RemoveGameAction(service, input);

        // Act
        action.execute();

        // Assert
        verify(service).removeGame(game);
    }
}