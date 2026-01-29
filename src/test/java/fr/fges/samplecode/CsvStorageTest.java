package fr.fges.samplecode;

import fr.fges.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvStorageTest {

    @Test
    void saveAndLoad_shouldPersistGamesCorrectly() throws Exception {
        // Arrange
        File tempFile = File.createTempFile("games", ".csv");
        tempFile.deleteOnExit();

        CsvStorage storage = new CsvStorage(tempFile.getAbsolutePath());
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "Strategy")
        );

        // Act
        storage.save(games);
        List<BoardGame> loaded = storage.load();

        // Assert
        assertEquals(1, loaded.size());
        assertEquals("Catan", loaded.get(0).title());
    }
}
