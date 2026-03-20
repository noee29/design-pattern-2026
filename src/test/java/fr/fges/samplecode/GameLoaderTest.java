package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameLoader;
import fr.fges.storage.StorageStrategy;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameLoaderTest {

    // ── load

    @Test
    void load_shouldReturnGamesFromStorage() throws IOException {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        List<BoardGame> expected = List.of(new BoardGame("Catan", 3, 4, "strategy"));
        when(storage.load()).thenReturn(expected);
        GameLoader loader = new GameLoader(storage);

        // Act
        List<BoardGame> result = loader.load();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void load_shouldReturnEmptyList_whenStorageThrowsIOException() throws IOException {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenThrow(new IOException("file not found"));
        GameLoader loader = new GameLoader(storage);

        // Act
        List<BoardGame> result = loader.load();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void load_shouldCallStorageLoad_exactlyOnce() throws IOException {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of());
        GameLoader loader = new GameLoader(storage);

        // Act
        loader.load();

        // Assert
        verify(storage, times(1)).load();
    }

    @Test
    void load_shouldReturnEmptyList_whenStorageReturnsEmptyList() throws IOException {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of());
        GameLoader loader = new GameLoader(storage);

        // Act
        List<BoardGame> result = loader.load();

        // Assert
        assertTrue(result.isEmpty());
    }
}