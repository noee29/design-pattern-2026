package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.storage.StorageStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Test
    void constructor_shouldLoadGamesFromStorage() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of(
                new BoardGame("Uno", 2, 10, "Card")
        ));

        // Act
        GameService service = new GameService(storage);

        // Assert
        assertEquals(1, service.getAllGames().size());
    }

    @Test
    void addGame_shouldAddGameAndSave() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of());

        GameService service = new GameService(storage);
        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");

        // Act
        service.addGame(game);

        // Assert
        assertEquals(1, service.getAllGames().size());
        verify(storage).save(anyList());
    }

    @Test
    void removeGame_shouldRemoveGameAndSave() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");
        when(storage.load()).thenReturn(List.of(game));

        GameService service = new GameService(storage);

        // Act
        service.removeGame(game);

        // Assert
        assertTrue(service.getAllGames().isEmpty());
        verify(storage).save(anyList());
    }

    @Test
    void gameExists_shouldReturnTrue_whenGameExists() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of(
                new BoardGame("Uno", 2, 10, "Card")
        ));

        GameService service = new GameService(storage);

        // Act
        boolean exists = service.gameExists("UNO");

        // Assert
        assertTrue(exists);
    }
}