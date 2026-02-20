package fr.fges.samplecode;

import fr.fges.tournament.Player;
import fr.fges.tournament.TournamentResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentResultTest {

    @Test
    void getRankedPlayers_shouldSortByPointsDescending() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        alice.addWin();  // 3 points
        bob.addLoss();   // 1 point
        TournamentResult result = new TournamentResult(List.of(bob, alice));

        // Act
        List<Player> ranked = result.getRankedPlayers();

        // Assert
        assertEquals("Alice", ranked.get(0).getName());
        assertEquals("Bob", ranked.get(1).getName());
    }

    @Test
    void getRankedPlayers_shouldSortByWins_whenPointsAreEqual() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        alice.addWin(); // 3 points, 1 win
        bob.addWin();   // 3 points, 1 win
        bob.addWin();   // 6 points, 2 wins
        alice.addWin(); // 6 points, 2 wins —> draw
        // Both have 6 points and 2 wins → alphabetical order
        TournamentResult result = new TournamentResult(List.of(bob, alice));

        // Act
        List<Player> ranked = result.getRankedPlayers();

        // Assert alphabetical order in case of a draw
        assertEquals("Alice", ranked.get(0).getName());
        assertEquals("Bob", ranked.get(1).getName());
    }

    @Test
    void getRankedPlayers_shouldNotMutateOriginalList() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        List<Player> original = List.of(alice, bob);
        TournamentResult result = new TournamentResult(original);

        // Act
        result.getRankedPlayers();

        // Assert
        assertEquals("Alice", original.get(0).getName());
        assertEquals("Bob", original.get(1).getName());
    }

    @Test
    void print_shouldNotThrow() {
        // Arrange
        Player alice = new Player("Alice");
        alice.addWin();
        TournamentResult result = new TournamentResult(List.of(alice));

        // Act & Assert
        assertDoesNotThrow(result::print);
    }
}