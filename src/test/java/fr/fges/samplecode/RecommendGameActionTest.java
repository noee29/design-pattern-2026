package fr.fges.samplecode;

import fr.fges.action.RecommendGameAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void execute_shouldRecommendGame() {
        // Arrange
        when(service.getAllGames()).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("7 Wonders", 3, 7, "strategy")
        ));
        when(input.getIntAtLeast("How many players?", 1)).thenReturn(4);

        // Act
        action.execute();

        // Assert passes if no exception thrown
    }
}