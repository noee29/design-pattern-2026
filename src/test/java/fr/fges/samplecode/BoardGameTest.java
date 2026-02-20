package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

    @Test
    void constructor_shouldSetAllFields() {
        // Arrange & Act
        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");

        // Assert
        assertEquals("Catan", game.getTitle());
        assertEquals(3, game.getMinPlayers());
        assertEquals(4, game.getMaxPlayers());
        assertEquals("Strategy", game.getCategory());
    }

    @Test
    void toString_shouldContainAllInfo() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");

        // Act
        String result = game.toString();

        // Assert
        assertTrue(result.contains("Catan"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("4"));
        assertTrue(result.contains("Strategy"));
    }

    @Test
    void equals_shouldReturnTrue_whenSameTitleIgnoringCase() {
        // Arrange
        BoardGame g1 = new BoardGame("UNO", 2, 10, "Card");
        BoardGame g2 = new BoardGame("uno", 1, 8, "Other");

        // Act
        boolean result = g1.equals(g2);

        // Assert
        assertTrue(result);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentTitles() {
        // Arrange
        BoardGame g1 = new BoardGame("Catan", 3, 4, "Strategy");
        BoardGame g2 = new BoardGame("Uno", 2, 10, "Card");

        // Act
        boolean result = g1.equals(g2);

        // Assert
        assertFalse(result);
    }

    @Test
    void equals_shouldReturnTrue_whenSameReference() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");

        // Act & Assert
        assertEquals(game, game);
    }

    @Test
    void equals_shouldReturnFalse_whenComparedToNull() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");

        // Act & Assert
        assertNotEquals(null, game);
    }

    @Test
    void hashCode_shouldBeEqual_whenSameTitleDifferentCase() {
        // Arrange
        BoardGame g1 = new BoardGame("Catan", 3, 4, "Strategy");
        BoardGame g2 = new BoardGame("catan", 2, 6, "Other");

        // Act & Assert
        assertEquals(g1.hashCode(), g2.hashCode());
    }
}