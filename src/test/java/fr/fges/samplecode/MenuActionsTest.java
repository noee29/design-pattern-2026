package fr.fges.samplecode;

import fr.fges.BoardGame;
import fr.fges.GameCollection;
import fr.fges.MenuActions;
import fr.fges.UserInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MenuActions class.
 */
class MenuActionsTest {

    @Test
    void listAllGames_shouldNotCrashWithEmptyCollection() {
        // Arrange
        GameCollection.getGames().clear();
        MenuActions actions = new MenuActions(new UserInput());

        // Act & Assert
        assertDoesNotThrow(() -> actions.listAllGames());
    }

    @Test
    void listAllGames_shouldNotCrashWithGames() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        GameCollection.addGame(game);
        MenuActions actions = new MenuActions(new UserInput());

        // Act & Assert
        assertDoesNotThrow(() -> actions.listAllGames());
    }

    @Test
    void addAndRemoveGame_shouldModifyCollection() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        GameCollection.addGame(game);

        // Assert
        assertEquals(1, GameCollection.getGames().size());

        // Act
        GameCollection.removeGame(game);

        // Assert
        assertEquals(0, GameCollection.getGames().size());
    }

    @Test
    void multipleOperations_shouldHandleAddAndRemove() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game1 = new BoardGame("Catan", 3, 4, "strategy");
        BoardGame game2 = new BoardGame("Carcassonne", 2, 5, "family");
        BoardGame game3 = new BoardGame("Azul", 2, 4, "abstract");

        // Act
        GameCollection.addGame(game1);
        GameCollection.addGame(game2);
        GameCollection.addGame(game3);

        // Assert
        assertEquals(3, GameCollection.getGames().size());

        // Act
        GameCollection.removeGame(game2);

        // Assert
        assertEquals(2, GameCollection.getGames().size());
        assertTrue(GameCollection.getGames().contains(game1));
        assertFalse(GameCollection.getGames().contains(game2));
        assertTrue(GameCollection.getGames().contains(game3));
    }

    @Test
    void findGameByTitle_shouldWorkCorrectly() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        GameCollection.addGame(game);

        // Act
        boolean found = false;
        for (BoardGame g : GameCollection.getGames()) {
            if (g.title().equals("Catan")) {
                found = true;
                break;
            }
        }

        // Assert
        assertTrue(found);
    }
}
