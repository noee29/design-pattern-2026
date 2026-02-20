package fr.fges.samplecode;

import fr.fges.tournament.KingOfHillStrategy;
import fr.fges.tournament.Match;
import fr.fges.tournament.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KingOfHillStrategyTest {

    private KingOfHillStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new KingOfHillStrategy();
    }

    @Test
    void getName_shouldReturnCorrectName() {
        // Act
        String name = strategy.getName();

        // Assert
        assertEquals("King of the Hill (winner stays)", name);
    }

    @Test
    void generateMatches_shouldReturnEmptyList() {
        // Arrange
        List<Player> players = List.of(new Player("Alice"), new Player("Bob"));

        // Act
        List<Match> matches = strategy.generateMatches(players);

        // Assert
        assertTrue(matches.isEmpty());
    }
}