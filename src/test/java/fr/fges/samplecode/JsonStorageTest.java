package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.storage.JsonStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonStorageTest {

    @TempDir
    Path tempDir;

    // ── load

    @Test
    void load_shouldReturnEmptyList_whenFileDoesNotExist() throws IOException {
        // Arrange
        JsonStorage storage = new JsonStorage("nonexistent_file_xyz.json");

        // Act
        List<BoardGame> result = storage.load();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void load_shouldReturnGames_afterSave() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.json").toFile();
        JsonStorage storage = new JsonStorage(file.getAbsolutePath());
        storage.save(List.of(new BoardGame("Catan", 3, 4, "strategy")));

        // Act
        List<BoardGame> result = storage.load();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Catan", result.get(0).getTitle());
    }

    @Test
    void load_shouldRestoreAllFields() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.json").toFile();
        JsonStorage storage = new JsonStorage(file.getAbsolutePath());
        storage.save(List.of(new BoardGame("Catan", 3, 4, "strategy")));

        // Act
        BoardGame loaded = storage.load().get(0);

        // Assert
        assertEquals("Catan", loaded.getTitle());
        assertEquals(3, loaded.getMinPlayers());
        assertEquals(4, loaded.getMaxPlayers());
        assertEquals("strategy", loaded.getCategory());
    }

    @Test
    void load_shouldRestoreMultipleGames() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.json").toFile();
        JsonStorage storage = new JsonStorage(file.getAbsolutePath());
        storage.save(List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("Pandemic", 2, 4, "coop")
        ));

        // Act
        List<BoardGame> result = storage.load();

        // Assert
        assertEquals(2, result.size());
    }

    // ── save

    @Test
    void save_shouldCreateFile() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.json").toFile();
        JsonStorage storage = new JsonStorage(file.getAbsolutePath());

        // Act
        storage.save(List.of(new BoardGame("Catan", 3, 4, "strategy")));

        // Assert
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void save_shouldOverwritePreviousContent() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.json").toFile();
        JsonStorage storage = new JsonStorage(file.getAbsolutePath());
        storage.save(List.of(new BoardGame("OldGame", 2, 4, "old")));

        // Act
        storage.save(List.of(new BoardGame("Catan", 3, 4, "strategy")));
        List<BoardGame> result = storage.load();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Catan", result.get(0).getTitle());
    }

    @Test
    void save_shouldHandleEmptyList() throws IOException {
        // Arrange
        File file = tempDir.resolve("empty.json").toFile();
        JsonStorage storage = new JsonStorage(file.getAbsolutePath());

        // Act & Assert
        assertDoesNotThrow(() -> storage.save(List.of()));
    }
}