package fr.fges.samplecode;

import fr.fges.tournament.Player;
import fr.fges.tournament.TournamentResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentResultTest {

    // ── getRankedPlayers

    @Test
    void getRankedPlayers_shouldSortByPointsDescending() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        alice.addWin();   // 3 points
        bob.addLoss();    // 1 point
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
        alice.addWin();  // 3 pts, 1 win
        bob.addLoss();   // 1 pt, 0 win
        bob.addLoss();   // 2 pts, 0 win
        bob.addLoss();   // 3 pts, 0 win — points égaux mais 0 victoire
        TournamentResult result = new TournamentResult(List.of(bob, alice));

        // Act
        List<Player> ranked = result.getRankedPlayers();

        // Assert — Alice en premier (même points, mais plus de victoires)
        assertEquals("Alice", ranked.get(0).getName());
    }

    @Test
    void getRankedPlayers_shouldSortAlphabetically_whenPointsAndWinsAreEqual() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        alice.addWin(); // 3 pts, 1 win
        bob.addWin();   // 3 pts, 1 win — égalité complète → ordre alphabétique
        TournamentResult result = new TournamentResult(List.of(bob, alice));

        // Act
        List<Player> ranked = result.getRankedPlayers();

        // Assert
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
    void getRankedPlayers_shouldReturnAllPlayers() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        Player charlie = new Player("Charlie");
        TournamentResult result = new TournamentResult(List.of(alice, bob, charlie));

        // Act
        List<Player> ranked = result.getRankedPlayers();

        // Assert
        assertEquals(3, ranked.size());
    }

    // ── print

    @Test
    void print_shouldNotThrow_withOnePlyer() {
        // Arrange
        Player alice = new Player("Alice");
        alice.addWin();
        TournamentResult result = new TournamentResult(List.of(alice));

        // Act & Assert
        assertDoesNotThrow(result::print);
    }

    @Test
    void print_shouldNotThrow_withMultiplePlayers() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        alice.addWin();
        bob.addLoss();
        TournamentResult result = new TournamentResult(List.of(alice, bob));

        // Act & Assert
        assertDoesNotThrow(result::print);
    }
}