package fr.fges.samplecode;

import fr.fges.tournament.Match;
import fr.fges.tournament.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    @Test
    void constructor_shouldSetBothPlayers() {
        // Arrange
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");

        // Act
        Match match = new Match(p1, p2);

        // Assert
        assertEquals(p1, match.getPlayer1());
        assertEquals(p2, match.getPlayer2());
    }

    @Test
    void getPlayer1_shouldReturnFirstPlayer() {
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
    void getPlayer2_shouldReturnSecondPlayer() {
        // Arrange
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        Match match = new Match(p1, p2);

        // Act
        Player result = match.getPlayer2();

        // Assert
        assertEquals("Bob", result.getName());
    }
}