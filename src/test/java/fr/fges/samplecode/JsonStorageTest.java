package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.storage.JsonStorage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonStorageTest {

    @Test
    void saveAndLoad_shouldPersistGamesCorrectly() throws Exception {
        // Arrange
        File tempFile = File.createTempFile("games", ".json");
        tempFile.deleteOnExit();

        JsonStorage storage = new JsonStorage(tempFile.getAbsolutePath());

        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "Strategy"),
                new BoardGame("Uno", 2, 10, "Family")
        );

        // Act
        storage.save(games);
        List<BoardGame> loaded = storage.load();

        // Assert
        assertEquals(2, loaded.size());
        assertEquals("Catan", loaded.get(0).title());
        assertEquals("Uno", loaded.get(1).title());
    }

    @Test
    void load_shouldReturnEmptyList_whenFileDoesNotExist() throws Exception {
        // Arrange
        JsonStorage storage = new JsonStorage("does-not-exist.json");

        // Act
        List<BoardGame> result = storage.load();

        // Assert
        assertTrue(result.isEmpty());
    }
}
