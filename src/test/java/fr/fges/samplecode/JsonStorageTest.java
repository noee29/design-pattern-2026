package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.storage.JsonStorage;
import fr.fges.storage.StorageStrategy;
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

    @Test
    void load_shouldReturnEmptyList_whenFileDoesNotExist() throws IOException {
        // Arrange
        StorageStrategy storage = new JsonStorage("nonexistent_file.json");

        // Act
        List<BoardGame> result = storage.load();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void save_shouldCreateFile() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.json").toFile();
        StorageStrategy storage = new JsonStorage(file.getAbsolutePath());

        // Act
        storage.save(List.of(new BoardGame("Catan", 3, 4, "strategy")));

        // Assert
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void saveAndLoad_shouldPersistGames() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.json").toFile();
        StorageStrategy storage = new JsonStorage(file.getAbsolutePath());
        List<BoardGame> gamesToSave = List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("Pandemic", 2, 4, "coop")
        );

        // Act
        storage.save(gamesToSave);
        List<BoardGame> loaded = storage.load();

        // Assert
        assertEquals(2, loaded.size());
        assertEquals("Catan", loaded.get(0).getTitle());
        assertEquals("Pandemic", loaded.get(1).getTitle());
    }

    @Test
    void load_shouldReturnEmptyList_whenFileIsEmpty() throws IOException {
        // Arrange
        File file = tempDir.resolve("empty.json").toFile();
        file.createNewFile();
        StorageStrategy storage = new JsonStorage(file.getAbsolutePath());

        // Act
        List<BoardGame> result = storage.load();

        // Assert
        assertTrue(result.isEmpty());
    }
}