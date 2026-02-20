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

    @Test
    void load_shouldReturnEmptyList_whenFileDoesNotExist() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.csv").toFile();
        CsvStorage storage = new CsvStorage(file.getAbsolutePath());

        // Act
        List<BoardGame> result = storage.load();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

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
    void saveAndLoad_shouldPersistGames() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.csv").toFile();
        CsvStorage storage = new CsvStorage(file.getAbsolutePath());
        storage.save(List.of(
                new BoardGame("Catan", 3, 4, "Strategy"),
                new BoardGame("Uno", 2, 10, "Card game")
        ));

        // Act
        List<BoardGame> loaded = storage.load();

        // Assert
        assertEquals(2, loaded.size());
        assertEquals("Catan", loaded.get(0).getTitle());
        assertEquals("Uno", loaded.get(1).getTitle());
    }

    @Test
    void saveAndLoad_shouldPreservePlayerCounts() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.csv").toFile();
        CsvStorage storage = new CsvStorage(file.getAbsolutePath());
        storage.save(List.of(new BoardGame("Catan", 3, 4, "Strategy")));

        // Act
        List<BoardGame> loaded = storage.load();

        // Assert
        assertEquals(3, loaded.get(0).getMinPlayers());
        assertEquals(4, loaded.get(0).getMaxPlayers());
    }
}