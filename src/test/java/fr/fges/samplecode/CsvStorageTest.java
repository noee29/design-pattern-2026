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
        List<BoardGame> games = storage.load();

        // Assert
        assertNotNull(games);
        assertTrue(games.isEmpty());
    }

    @Test
    void save_shouldCreateCsvFile() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.csv").toFile();
        CsvStorage storage = new CsvStorage(file.getAbsolutePath());

        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "Strategy")
        );

        // Act
        storage.save(games);

        // Assert
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void load_shouldReadGamesPreviouslySaved() throws IOException {
        // Arrange
        File file = tempDir.resolve("games.csv").toFile();
        CsvStorage storage = new CsvStorage(file.getAbsolutePath());

        List<BoardGame> gamesToSave = List.of(
                new BoardGame("Catan", 3, 4, "Strategy"),
                new BoardGame("Uno", 2, 10, "Card game")
        );

        storage.save(gamesToSave);

        // Act
        List<BoardGame> loadedGames = storage.load();

        // Assert
        assertEquals(2, loadedGames.size());
        assertEquals("Catan", loadedGames.get(0).getTitle());
        assertEquals("Uno", loadedGames.get(1).getTitle());
    }
}