package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameFinder;
import fr.fges.service.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameFinderTest {

    private GameRepository repository;
    private GameFinder finder;

    @BeforeEach
    void setUp() {
        repository = new GameRepository();
        finder = new GameFinder(repository);
    }

    // ── findGamesByPlayers

    @Test
    void findGamesByPlayers_shouldReturnEmptyList_whenRepositoryIsEmpty() {
        // Arrange — repository vide

        // Act
        List<BoardGame> result = finder.findGamesByPlayers(4);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findGamesByPlayers_shouldReturnCompatibleGames() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));
        repository.add(new BoardGame("SoloGame", 1, 1, "solo"));

        // Act
        List<BoardGame> result = finder.findGamesByPlayers(3);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Catan", result.get(0).getTitle());
    }

    @Test
    void findGamesByPlayers_shouldExcludeGamesWithTooFewSlots() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy")); // max 4 joueurs

        // Act
        List<BoardGame> result = finder.findGamesByPlayers(5); // 5 > max

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findGamesByPlayers_shouldExcludeGamesWithTooManyRequired() {
        // Arrange
        repository.add(new BoardGame("BigGame", 6, 10, "party")); // min 6 joueurs

        // Act
        List<BoardGame> result = finder.findGamesByPlayers(4); // 4 < min

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findGamesByPlayers_shouldSortResultsAlphabetically() {
        // Arrange
        repository.add(new BoardGame("Pandemic", 2, 4, "coop"));
        repository.add(new BoardGame("7 Wonders", 3, 7, "strategy"));
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));

        // Act
        List<BoardGame> result = finder.findGamesByPlayers(4);

        // Assert
        assertEquals("7 Wonders", result.get(0).getTitle());
        assertEquals("Catan", result.get(1).getTitle());
        assertEquals("Pandemic", result.get(2).getTitle());
    }

    @Test
    void findGamesByPlayers_shouldReturnGameAtExactMinBoundary() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));

        // Act
        List<BoardGame> result = finder.findGamesByPlayers(3); // égal au min

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findGamesByPlayers_shouldReturnGameAtExactMaxBoundary() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));

        // Act
        List<BoardGame> result = finder.findGamesByPlayers(4); // égal au max

        // Assert
        assertEquals(1, result.size());
    }

    // ── gameExists

    @Test
    void gameExists_shouldReturnFalse_whenRepositoryIsEmpty() {
        // Arrange — repository vide

        // Act
        boolean result = finder.gameExists("Catan");

        // Assert
        assertFalse(result);
    }

    @Test
    void gameExists_shouldReturnTrue_whenGameIsPresent() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));

        // Act
        boolean result = finder.gameExists("Catan");

        // Assert
        assertTrue(result);
    }

    @Test
    void gameExists_shouldBeCaseInsensitive() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));

        // Act
        boolean result = finder.gameExists("catan");

        // Assert
        assertTrue(result);
    }

    @Test
    void gameExists_shouldReturnFalse_whenGameIsAbsent() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));

        // Act
        boolean result = finder.gameExists("Pandemic");

        // Assert
        assertFalse(result);
    }
}