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

    @Test
    void recommend_shouldReturnCompatibleGame() {
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
    void recommend_shouldReturnEmpty_whenNoCompatibleGame() {
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
    void recommend_shouldReturnEmpty_whenListIsEmpty() {
        // Arrange
        List<BoardGame> games = List.of();

        // Act
        Optional<BoardGame> result = service.recommend(games, 3);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void recommend_shouldOnlyConsiderGamesWithExactPlayerCount() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("OnlyFor2", 2, 2, "strategy")
        );

        // Act
        Optional<BoardGame> result = service.recommend(games, 3);

        // Assert
        assertTrue(result.isEmpty());
    }
}