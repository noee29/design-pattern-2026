package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.storage.JsonStorage;
import fr.fges.storage.StorageStrategy;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonStorageTest {

    @Test
    void load_shouldReturnEmptyList_whenFileDoesNotExist() throws Exception {
        // Arrange
        StorageStrategy storage = new JsonStorage("unknown.json");

        // Act
        List<BoardGame> games = storage.load();

        // Assert
        assertTrue(games.isEmpty());
    }

    @Test
    void saveAndLoad_shouldPersistGames() throws Exception {
        // Arrange
        File file = File.createTempFile("games", ".json");
        StorageStrategy storage = new JsonStorage(file.getAbsolutePath());

        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");

        // Act
        storage.save(List.of(game));
        List<BoardGame> loaded = storage.load();

        // Assert
        assertEquals(1, loaded.size());
        assertEquals("Catan", loaded.get(0).getTitle());

        file.delete();
    }
}