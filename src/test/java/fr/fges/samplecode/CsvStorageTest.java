package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.storage.CsvStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvStorageTest {

    @TempDir
    Path tempDir;

    // ── load

    @Test
    void load_shouldReturnEmptyList_whenFileDoesNotExist() throws IOException {
        // Arrange
        CsvStorage storage = new CsvStorage(tempDir.resolve("nofile.csv").toString());

        // Act
        List<BoardGame> result = storage.load();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void load_shouldReturnGames_afterSave() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.csv").toFile();
        CsvStorage storage = new CsvStorage(file.getAbsolutePath());
        storage.save(List.of(new BoardGame("Catan", 3, 4, "Strategy")));

        // Act
        List<BoardGame> result = storage.load();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Catan", result.get(0).getTitle());
    }

    @Test
    void load_shouldRestoreAllFields() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.csv").toFile();
        CsvStorage storage = new CsvStorage(file.getAbsolutePath());
        storage.save(List.of(new BoardGame("Catan", 3, 4, "Strategy")));

        // Act
        BoardGame loaded = storage.load().get(0);

        // Assert
        assertEquals("Catan", loaded.getTitle());
        assertEquals(3, loaded.getMinPlayers());
        assertEquals(4, loaded.getMaxPlayers());
        assertEquals("Strategy", loaded.getCategory());
    }

    @Test
    void load_shouldRestoreMultipleGames() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.csv").toFile();
        CsvStorage storage = new CsvStorage(file.getAbsolutePath());
        storage.save(List.of(
                new BoardGame("Catan", 3, 4, "Strategy"),
                new BoardGame("Uno", 2, 10, "Card")
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
        File file = tempDir.resolve("games.csv").toFile();
        CsvStorage storage = new CsvStorage(file.getAbsolutePath());

        // Act
        storage.save(List.of(new BoardGame("Catan", 3, 4, "Strategy")));

        // Assert
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void save_shouldOverwritePreviousContent() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.csv").toFile();
        CsvStorage storage = new CsvStorage(file.getAbsolutePath());
        storage.save(List.of(new BoardGame("OldGame", 2, 4, "old")));

        // Act
        storage.save(List.of(new BoardGame("Catan", 3, 4, "Strategy")));
        List<BoardGame> result = storage.load();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Catan", result.get(0).getTitle());
    }

    @Test
    void save_shouldHandleEmptyList() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.csv").toFile();
        CsvStorage storage = new CsvStorage(file.getAbsolutePath());

        // Act
        storage.save(List.of());
        List<BoardGame> result = storage.load();

        // Assert
        assertTrue(result.isEmpty());
    }
}