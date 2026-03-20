package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameRepositoryTest {

    private GameRepository repository;

    @BeforeEach
    void setUp() {
        repository = new GameRepository();
    }

    // ── getGames

    @Test
    void getGames_shouldReturnEmptyList_initially() {
        // Arrange — repository vide depuis setUp()

        // Act
        List<BoardGame> games = repository.getGames();

        // Assert
        assertTrue(games.isEmpty());
    }

    // ── add

    @Test
    void add_shouldIncreaseSize() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        repository.add(game);

        // Assert
        assertEquals(1, repository.getGames().size());
    }

    @Test
    void add_shouldStoreTheGame() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        repository.add(game);

        // Assert
        assertTrue(repository.getGames().contains(game));
    }

    @Test
    void add_shouldStoreMultipleGames() {
        // Arrange
        BoardGame g1 = new BoardGame("Catan", 3, 4, "strategy");
        BoardGame g2 = new BoardGame("Pandemic", 2, 4, "coop");

        // Act
        repository.add(g1);
        repository.add(g2);

        // Assert
        assertEquals(2, repository.getGames().size());
    }

    // ── remove

    @Test
    void remove_shouldDecreaseSize() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        repository.add(game);

        // Act
        repository.remove(game);

        // Assert
        assertEquals(0, repository.getGames().size());
    }

    @Test
    void remove_shouldDeleteTheCorrectGame() {
        // Arrange
        BoardGame catan = new BoardGame("Catan", 3, 4, "strategy");
        BoardGame pandemic = new BoardGame("Pandemic", 2, 4, "coop");
        repository.add(catan);
        repository.add(pandemic);

        // Act
        repository.remove(catan);

        // Assert
        assertFalse(repository.getGames().contains(catan));
        assertTrue(repository.getGames().contains(pandemic));
    }

    // ── setGames

    @Test
    void setGames_shouldReplaceExistingGames() {
        // Arrange
        repository.add(new BoardGame("OldGame", 2, 4, "old"));
        List<BoardGame> newGames = List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("Pandemic", 2, 4, "coop")
        );

        // Act
        repository.setGames(newGames);

        // Assert
        assertEquals(2, repository.getGames().size());
        assertFalse(repository.getGames().stream().anyMatch(g -> g.getTitle().equals("OldGame")));
    }

    @Test
    void setGames_shouldStoreAllNewGames() {
        // Arrange
        List<BoardGame> newGames = List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("Pandemic", 2, 4, "coop")
        );

        // Act
        repository.setGames(newGames);

        // Assert
        assertTrue(repository.getGames().stream().anyMatch(g -> g.getTitle().equals("Catan")));
        assertTrue(repository.getGames().stream().anyMatch(g -> g.getTitle().equals("Pandemic")));
    }

    // ── findAll

    @Test
    void findAll_shouldReturnCopy_notOriginalReference() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));

        // Act
        List<BoardGame> copy = repository.findAll();
        copy.clear();

        // Assert — la modification de la copie ne doit pas affecter le repository
        assertEquals(1, repository.getGames().size());
    }

    @Test
    void findAll_shouldContainAllGames() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));
        repository.add(new BoardGame("Pandemic", 2, 4, "coop"));

        // Act
        List<BoardGame> all = repository.findAll();

        // Assert
        assertEquals(2, all.size());
    }
}