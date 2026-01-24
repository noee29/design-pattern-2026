package fr.fges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Menu class.
 * Tests each public method using the AAA pattern:
 * - Arrange: Prepare test data
 * - Act: Call the method
 * - Assert: Check the result
 */
class MenuTest {


    @Test
    void displayMainMenu_shouldNotCrash() {
        // Arrange
        // Nothing to prepare

        // Act & Assert
        assertDoesNotThrow(() -> Menu.displayMainMenu());
    }

    // ========== Tests for listAllGames() ==========

    @Test
    void listAllGames_shouldNotCrashWithEmptyCollection() {
        // Arrange
        GameCollection.getGames().clear();

        // Act & Assert
        assertDoesNotThrow(() -> Menu.listAllGames());
    }

    @Test
    void listAllGames_shouldNotCrashWithGames() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        GameCollection.addGame(game);

        // Act & Assert
        assertDoesNotThrow(() -> Menu.listAllGames());
    }


    @Test
    void addGameToCollection_shouldAddTheGame() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        GameCollection.addGame(game);

        // Assert
        assertEquals(1, GameCollection.getGames().size());
        assertTrue(GameCollection.getGames().contains(game));
    }

    @Test
    void removeGameFromCollection_shouldRemoveTheGame() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        GameCollection.addGame(game);

        // Act
        GameCollection.removeGame(game);

        // Assert
        assertEquals(0, GameCollection.getGames().size());
    }

    @Test
    void findGameByTitle_shouldFindExistingGame() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        GameCollection.addGame(game);
        String titleToFind = "Catan";

        // Act
        boolean found = false;
        for (BoardGame g : GameCollection.getGames()) {
            if (g.title().equals(titleToFind)) {
                found = true;
                break;
            }
        }

        // Assert
        assertTrue(found);
    }

    @Test
    void findGameByTitle_shouldNotFindNonExistentGame() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        GameCollection.addGame(game);
        String titleToFind = "NonExistentGame";

        // Act
        boolean found = false;
        for (BoardGame g : GameCollection.getGames()) {
            if (g.title().equals(titleToFind)) {
                found = true;
                break;
            }
        }

        // Assert
        assertFalse(found);
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
    void viewAllGames_shouldWorkAfterAddingGames() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game1 = new BoardGame("Catan", 3, 4, "strategy");
        BoardGame game2 = new BoardGame("Carcassonne", 2, 5, "family");
        GameCollection.addGame(game1);
        GameCollection.addGame(game2);

        // Act & Assert
        assertDoesNotThrow(() -> Menu.listAllGames());
        assertEquals(2, GameCollection.getGames().size());
    }
}