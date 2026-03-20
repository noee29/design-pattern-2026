package fr.fges.samplecode;

import fr.fges.tournament.Match;
import fr.fges.tournament.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    // ── Constructeur

    @Test
    void constructor_shouldSetPlayer1() {
        // Arrange
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");

        // Act
        Match match = new Match(p1, p2);

        // Assert
        assertEquals(p1, match.getPlayer1());
    }

    @Test
    void constructor_shouldSetPlayer2() {
        // Arrange
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");

        // Act
        Match match = new Match(p1, p2);

        // Assert
        assertEquals(p2, match.getPlayer2());
    }

    // ── getPlayer1 / getPlayer2

    @Test
    void getPlayer1_shouldReturnFirstPlayerByName() {
        // Arrange
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        Match match = new Match(p1, p2);

        // Act
        Player result = match.getPlayer1();

        // Assert
        assertEquals("Alice", result.getName());
    }

    @Test
    void getPlayer2_shouldReturnSecondPlayerByName() {
        // Arrange
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        Match match = new Match(p1, p2);

        // Act
        Player result = match.getPlayer2();

        // Assert
        assertEquals("Bob", result.getName());
    }

    @Test
    void players_shouldBeDistinctObjects() {
        // Arrange
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        Match match = new Match(p1, p2);

        // Act & Assert
        assertNotSame(match.getPlayer1(), match.getPlayer2());
    }
}