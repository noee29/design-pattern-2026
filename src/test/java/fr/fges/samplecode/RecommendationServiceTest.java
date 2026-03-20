package fr.fges.samplecode;

import fr.fges.businesslogic.RecommendationService;
import fr.fges.model.BoardGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationServiceTest {

    private RecommendationService service;

    @BeforeEach
    void setUp() {
        service = new RecommendationService();
    }

    // ── recommend

    @Test
    void recommend_shouldReturnEmpty_whenListIsEmpty() {
        // Arrange
        List<BoardGame> games = List.of();

        // Act
        Optional<BoardGame> result = service.recommend(games, 3);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void recommend_shouldReturnEmpty_whenNoGameCompatible() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("SoloGame", 1, 1, "solo")
        );

        // Act
        Optional<BoardGame> result = service.recommend(games, 4);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void recommend_shouldReturnPresent_whenCompatibleGameExists() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "strategy")
        );

        // Act
        Optional<BoardGame> result = service.recommend(games, 4);

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    void recommend_shouldReturnCompatibleGame_respecting_minPlayers() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "strategy")
        );

        // Act
        Optional<BoardGame> result = service.recommend(games, 2); // sous le min

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void recommend_shouldReturnCompatibleGame_respecting_maxPlayers() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "strategy")
        );

        // Act
        Optional<BoardGame> result = service.recommend(games, 5); // au-dessus du max

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void recommend_shouldReturnGameWithinRange() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("Pandemic", 2, 4, "coop")
        );

        // Act
        Optional<BoardGame> result = service.recommend(games, 4);

        // Assert
        assertTrue(result.isPresent());
        assertTrue(result.get().getMinPlayers() <= 4);
        assertTrue(result.get().getMaxPlayers() >= 4);
    }

    @Test
    void recommend_shouldReturnTheOnlyCompatibleGame_whenOnlyOneMatches() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("SoloGame", 1, 1, "solo")
        );

        // Act
        Optional<BoardGame> result = service.recommend(games, 3);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Catan", result.get().getTitle());
    }
}