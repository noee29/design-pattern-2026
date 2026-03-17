package fr.fges.samplecode;

import fr.fges.ui.FindGamesByPlayersAction;
import fr.fges.model.BoardGame;
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
        public void save(List<BoardGame> games) {}
    }

    static class FakeInputFor4Players extends UserInput {
        @Override
        public int getIntAtLeast(String label, int min) {
            return 4;
        }
    }

    static class FakeInputFor99Players extends UserInput {
        @Override
        public int getIntAtLeast(String label, int min) {
            return 99;
        }
    }

    @Test
    void getLabel_shouldReturnCorrectLabel() {
        // Arrange
        GameService service = new GameService(new FakeStorage());
        FindGamesByPlayersAction action = new FindGamesByPlayersAction(service, new FakeInputFor4Players());

        // Act
        String label = action.getLabel();

        // Assert
        assertEquals("Games for X Players", label);
    }

    @Test
    void execute_shouldNotThrow_whenGamesAreFound() {
        // Arrange
        GameService service = new GameService(new FakeStorage());
        FindGamesByPlayersAction action = new FindGamesByPlayersAction(service, new FakeInputFor4Players());

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldNotThrow_whenNoGamesFound() {
        // Arrange
        GameService service = new GameService(new FakeStorage());
        FindGamesByPlayersAction action = new FindGamesByPlayersAction(service, new FakeInputFor99Players());

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void service_shouldFilterAndSortCorrectly_forFourPlayers() {
        // Arrange
        GameService service = new GameService(new FakeStorage());

        // Act
        List<BoardGame> result = service.findGamesByPlayers(4);

        // Assert
        assertEquals(3, result.size());
        assertEquals("7 Wonders", result.get(0).getTitle());
        assertEquals("Catan", result.get(1).getTitle());
        assertEquals("Pandemic", result.get(2).getTitle());
    }
}