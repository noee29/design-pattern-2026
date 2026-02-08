package fr.fges.samplecode;

import fr.fges.action.FindGamesByPlayersAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.storage.StorageStrategy;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FindGamesByPlayersActionTest {

    static class FakeStorage implements StorageStrategy {

        @Override
        public List<BoardGame> load() {
            return List.of(
                    new BoardGame("Catan", 3, 4, "strategy"),
                    new BoardGame("Pandemic", 2, 4, "coop"),
                    new BoardGame("SoloGame", 1, 1, "solo"),
                    new BoardGame("7 Wonders", 3, 7, "strategy")
            );
        }

        @Override
        public void save(List<BoardGame> games) {
        }
    }

    static class FakeInput extends UserInput {
        @Override
        public int getIntAtLeast(String label, int min) {
            return 4;
        }
    }

    @Test
    void findGamesByPlayers_shouldFilterAndSortCorrectly() {

        GameService service = new GameService(new FakeStorage());

        var result = service.findGamesByPlayers(4);

        assertEquals(3, result.size());

        assertEquals("7 Wonders", result.get(0).getTitle());
        assertEquals("Catan", result.get(1).getTitle());
        assertEquals("Pandemic", result.get(2).getTitle());
    }

    @Test
    void action_execute_shouldNotThrow() {

        GameService service = new GameService(new FakeStorage());
        FindGamesByPlayersAction action =
                new FindGamesByPlayersAction(service, new FakeInput());

        assertDoesNotThrow(action::execute);
    }
}
