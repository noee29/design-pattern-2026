package fr.fges.samplecode;

import fr.fges.BoardGame;
import fr.fges.GameRepository;
import fr.fges.StorageStrategy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameRepositoryTest {

    @Test
    void constructor_shouldLoadGamesFromStorage() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        when(storage.load()).thenReturn(List.of(game));

        // Act
        GameRepository repository = new GameRepository(storage);

        // Assert
        assertEquals(1, repository.getGames().size());
        assertTrue(repository.getGames().contains(game));
        verify(storage, times(1)).load();
    }

    @Test
    void addGame_shouldSaveToStorage() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of());

        GameRepository repository = new GameRepository(storage);
        BoardGame game = new BoardGame("Azul", 2, 4, "abstract");

        // Act
        repository.addGame(game);

        // Assert
        assertEquals(1, repository.getGames().size());
        verify(storage, times(1)).save(repository.getGames());
    }

    @Test
    void removeGame_shouldSaveToStorage() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        when(storage.load()).thenReturn(List.of(game));

        GameRepository repository = new GameRepository(storage);

        // Act
        repository.removeGame(game);

        // Assert
        assertTrue(repository.isEmpty());
        verify(storage, times(1)).save(repository.getGames());
    }
}
