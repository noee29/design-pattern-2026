package fr.fges.samplecode;

import fr.fges.action.RemoveGameAction;
import fr.fges.history.ActionHistory;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class RemoveGameActionTest {

    private GameService service;
    private UserInput input;
    private ActionHistory history;
    private RemoveGameAction action;

    @BeforeEach
    void setUp() {
        service = mock(GameService.class);
        input = mock(UserInput.class);
        history = new ActionHistory();
        action = new RemoveGameAction(service, input, history);
    }

    @Test
    void execute_shouldRemoveSelectedGameAndPushToHistory() {
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        when(service.getAllGames()).thenReturn(List.of(game));
        when(input.getIntBetween("Select game number to remove", 1, 1)).thenReturn(1);

        action.execute();

        verify(service, times(1)).removeGame(game);
        assert(history.size() == 1);
    }

    @Test
    void undo_shouldAddBackLastRemovedGame() {
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        when(service.getAllGames()).thenReturn(List.of(game));
        when(input.getIntBetween("Select game number to remove", 1, 1)).thenReturn(1);

        action.execute();
        action.undo();

        verify(service, times(1)).addGame(game);
    }
}