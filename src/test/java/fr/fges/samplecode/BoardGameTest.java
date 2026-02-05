package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

    @Test
    void constructor_shouldSetAllFields() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");

        // Act & Assert
        assertEquals("Catan", game.getTitle());
        assertEquals(3, game.getMinPlayers());
        assertEquals(4, game.getMaxPlayers());
        assertEquals("Strategy", game.getCategory());
    }

    @Test
    void equals_shouldIgnoreCaseOnTitle() {
        // Arrange
        BoardGame g1 = new BoardGame("UNO", 2, 10, "Card");
        BoardGame g2 = new BoardGame("uno", 1, 8, "Other");

        // Act
        boolean result = g1.equals(g2);

        // Assert
        assertTrue(result);
    }

    @Test
    void hashCode_shouldBeSameForSameTitleIgnoringCase() {
        // Arrange
        BoardGame g1 = new BoardGame("Catan", 3, 4, "Strategy");
        BoardGame g2 = new BoardGame("catan", 2, 6, "Other");

        // Act & Assert
        assertEquals(g1.hashCode(), g2.hashCode());
    }
}