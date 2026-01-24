package fr.fges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GameCollection class.
 * Tests each public method using the AAA pattern:
 * - Arrange: Prepare test data
 * - Act: Call the method
 * - Assert: Check the result
 */
class GameCollectionTest {

    @Test
    void addGame_shouldAddOneGame() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        GameCollection.addGame(game);

        // Assert
        assertEquals(1, GameCollection.getGames().size());
    }

    @Test
    void addGame_shouldContainTheAddedGame() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        GameCollection.addGame(game);

        // Assert
        assertTrue(GameCollection.getGames().contains(game));
    }

    @Test
    void addGame_shouldAddMultipleGames() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game1 = new BoardGame("Catan", 3, 4, "strategy");
        BoardGame game2 = new BoardGame("Carcassonne", 2, 5, "family");

        // Act
        GameCollection.addGame(game1);
        GameCollection.addGame(game2);

        // Assert
        assertEquals(2, GameCollection.getGames().size());
    }


    @Test
    void removeGame_shouldRemoveTheGame() {
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
    void removeGame_shouldNotContainRemovedGame() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        GameCollection.addGame(game);

        // Act
        GameCollection.removeGame(game);

        // Assert
        assertFalse(GameCollection.getGames().contains(game));
    }

    @Test
    void removeGame_shouldRemoveOnlySpecifiedGame() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game1 = new BoardGame("Catan", 3, 4, "strategy");
        BoardGame game2 = new BoardGame("Carcassonne", 2, 5, "family");
        GameCollection.addGame(game1);
        GameCollection.addGame(game2);

        // Act
        GameCollection.removeGame(game1);

        // Assert
        assertEquals(1, GameCollection.getGames().size());
        assertTrue(GameCollection.getGames().contains(game2));
    }

    // ========== Tests for getGames() ==========

    @Test
    void getGames_shouldReturnEmptyListWhenNoGames() {
        // Arrange
        GameCollection.getGames().clear();

        // Act
        var games = GameCollection.getGames();

        // Assert
        assertTrue(games.isEmpty());
    }

    @Test
    void getGames_shouldReturnAllAddedGames() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game1 = new BoardGame("Catan", 3, 4, "strategy");
        BoardGame game2 = new BoardGame("Carcassonne", 2, 5, "family");
        GameCollection.addGame(game1);
        GameCollection.addGame(game2);

        // Act
        var games = GameCollection.getGames();

        // Assert
        assertEquals(2, games.size());
    }

    @Test
    void viewAllGames_shouldNotCrashWithEmptyCollection() {
        // Arrange
        GameCollection.getGames().clear();

        // Act & Assert
        assertDoesNotThrow(() -> GameCollection.viewAllGames());
    }

    @Test
    void viewAllGames_shouldNotCrashWithGames() {
        // Arrange
        GameCollection.getGames().clear();
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        GameCollection.addGame(game);

        // Act & Assert
        assertDoesNotThrow(() -> GameCollection.viewAllGames());
    }

    @Test
    void setStorageFile_shouldSetTheFilePath() {
        // Arrange
        String filePath = "test_games.json";

        // Act
        GameCollection.setStorageFile(filePath);

        // Assert
        // We cannot directly check the private storageFile variable
        // but we can verify the method doesn't crash
        assertDoesNotThrow(() -> GameCollection.setStorageFile(filePath));
    }

    @Test
    void loadFromFile_shouldNotCrashWhenFileDoesNotExist() {
        // Arrange
        GameCollection.getGames().clear();
        GameCollection.setStorageFile("nonexistent_file.json");

        // Act
        GameCollection.loadFromFile();

        // Assert
        // The collection should remain empty since file doesn't exist
        assertEquals(0, GameCollection.getGames().size());
    }
}