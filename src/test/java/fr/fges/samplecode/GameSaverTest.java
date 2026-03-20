package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameSaver;
import fr.fges.storage.StorageStrategy;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameSaverTest {

    // ── save

    @Test
    void save_shouldDelegateToStorage() throws IOException {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        GameSaver saver = new GameSaver(storage);
        List<BoardGame> games = List.of(new BoardGame("Catan", 3, 4, "strategy"));

        // Act
        saver.save(games);

        // Assert
        verify(storage, times(1)).save(games);
    }

    @Test
    void save_shouldNotThrow_whenStorageThrowsIOException() throws IOException {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        doThrow(new IOException("disk full")).when(storage).save(anyList());
        GameSaver saver = new GameSaver(storage);

        // Act & Assert — l'IOException est absorbée, pas de propagation
        assertDoesNotThrow(() -> saver.save(List.of()));
    }

    @Test
    void save_shouldPassCorrectListToStorage() throws IOException {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        GameSaver saver = new GameSaver(storage);
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("Pandemic", 2, 4, "coop")
        );

        // Act
        saver.save(games);

        // Assert
        verify(storage).save(argThat(list -> list.size() == 2));
    }
}