package fr.fges.samplecode;

import fr.fges.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameRepositoryTest {

    private StorageStrategy storage;
    private GameRepository repository;

    @BeforeEach
    void setUp() throws IOException {
        // Arrange
        storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of());

        repository = new GameRepository(storage);
    }

    @Test
    void addGame_shouldAddGame_whenTitleIsUnique() throws IOException {
        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");

        repository.addGame(game);

        assertEquals(1, repository.getGames().size());
        verify(storage).save(any());
    }

    @Test
    void addGame_shouldThrowException_whenTitleAlreadyExists() {
        // Arrange
        BoardGame game1 = new BoardGame("Catan", 3, 4, "Strategy");
        BoardGame game2 = new BoardGame("CATAN", 2, 6, "Family");

        repository.addGame(game1);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> repository.addGame(game2));
    }

    @Test
    void findCompatibleGames_shouldReturnMatchingGames() {
        // Arrange
        repository.addGame(new BoardGame("Catan", 3, 4, "Strategy"));
        repository.addGame(new BoardGame("Uno", 2, 10, "Family"));

        // Act
        List<BoardGame> result = repository.findCompatibleGames(4);

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void getGamesSortedByTitle_shouldReturnAlphabeticalOrder() {
        // Arrange
        repository.addGame(new BoardGame("Zelda", 1, 4, "Adventure"));
        repository.addGame(new BoardGame("Catan", 3, 4, "Strategy"));

        // Act
        List<BoardGame> sorted = repository.getGamesSortedByTitle();

        // Assert
        assertEquals("Catan", sorted.get(0).title());
        assertEquals("Zelda", sorted.get(1).title());
    }
}
