package fr.fges.samplecode;

import fr.fges.tournament.ChampionshipStrategy;
import fr.fges.tournament.Match;
import fr.fges.tournament.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChampionshipStrategyTest {

    private ChampionshipStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new ChampionshipStrategy();
    }

    @Test
    void getName_shouldReturnCorrectName() {
        // Act
        String name = strategy.getName();

        // Assert
        assertEquals("Championship (everyone plays everyone)", name);
    }

    @Test
    void generateMatches_shouldReturnCorrectNumberOfMatches_forThreePlayers() {
        // Arrange — 3 players → 3 matchs (combinations of 2 out of 3)
        List<Player> players = List.of(
                new Player("Alice"),
                new Player("Bob"),
                new Player("Charlie")
        );

        // Act
        List<Match> matches = strategy.generateMatches(players);

        // Assert
        assertEquals(3, matches.size());
    }

    @Test
    void generateMatches_shouldReturnCorrectNumberOfMatches_forFourPlayers() {
        // Arrange — 4 joueurs → 6 matchs
        List<Player> players = List.of(
                new Player("Alice"),
                new Player("Bob"),
                new Player("Charlie"),
                new Player("Dave")
        );

        // Act
        List<Match> matches = strategy.generateMatches(players);

        // Assert
        assertEquals(6, matches.size());
    }

    @Test
    void generateMatches_shouldReturnEmptyList_forOnePlayer() {
        // Arrange
        List<Player> players = List.of(new Player("Alice"));

        // Act
        List<Match> matches = strategy.generateMatches(players);

        // Assert
        assertTrue(matches.isEmpty());
    }

    @Test
    void generateMatches_shouldNeverMatchPlayerAgainstThemself() {
        // Arrange
        List<Player> players = List.of(
                new Player("Alice"),
                new Player("Bob"),
                new Player("Charlie")
        );

        // Act
        List<Match> matches = strategy.generateMatches(players);

        // Assert
        for (Match match : matches) {
            assertNotEquals(match.getPlayer1().getName(), match.getPlayer2().getName());
        }
    }
}