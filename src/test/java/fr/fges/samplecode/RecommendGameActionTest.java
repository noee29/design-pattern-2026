package fr.fges.samplecode;

import fr.fges.action.RecommendGameAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class RecommendGameActionTest {

    @Test
    void execute_shouldAskForPlayerCountAndGetGames() {
        // Arrange
        GameService service = mock(GameService.class);
        UserInput input = mock(UserInput.class);

        when(service.getAllGames()).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "Strategy")
        ));
        when(input.getIntAtLeast(anyString(), anyInt())).thenReturn(4);

        RecommendGameAction action = new RecommendGameAction(service, input);

        // Act
        action.execute();

        // Assert
        verify(service).getAllGames();
        verify(input).getIntAtLeast(anyString(), anyInt());
    }
}