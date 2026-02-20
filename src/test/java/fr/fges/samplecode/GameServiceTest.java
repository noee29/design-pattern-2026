package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.storage.StorageStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    private StorageStrategy storage;
    private GameService service;

    @BeforeEach
    void setUp() throws IOException {
        storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of());
        service = new GameService(storage);
    }

    @Test
    void addGame_shouldAddGameToCollection() throws IOException {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        service.addGame(game);

        // Assert
        assertTrue(service.gameExists("Catan"));
    }

    @Test
    void addGame_shouldTriggerSave() throws IOException {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        service.addGame(game);

        // Assert
        verify(storage, times(1)).save(anyList());
    }

    @Test
    void removeGame_shouldRemoveGameFromCollection() throws IOException {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        service.addGame(game);

        // Act
        service.removeGame(game);

        // Assert
        assertFalse(service.gameExists("Catan"));
    }

    @Test
    void removeGame_shouldTriggerSave() throws IOException {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        service.addGame(game);

        // Act
        service.removeGame(game);

        // Assert — 1 save pour addGame + 1 save pour removeGame
        verify(storage, times(2)).save(anyList());
    }

    @Test
    void getAllGames_shouldReturnCopy_notOriginalReference() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        service.addGame(game);

        // Act
        List<BoardGame> copy = service.getAllGames();
        copy.clear();

        // Assert
        assertEquals(1, service.getAllGames().size());
    }

    @Test
    void gameExists_shouldReturnTrue_whenGameIsInCollection() {
        // Arrange
        service.addGame(new BoardGame("Catan", 3, 4, "strategy"));

        // Act
        boolean result = service.gameExists("catan"); // insensible à la casse

        // Assert
        assertTrue(result);
    }

    @Test
    void gameExists_shouldReturnFalse_whenGameIsNotInCollection() {
        // Arrange — collection vide

        // Act
        boolean result = service.gameExists("Catan");

        // Assert
        assertFalse(result);
    }

    @Test
    void findGamesByPlayers_shouldReturnOnlyCompatibleGames() {
        // Arrange
        service.addGame(new BoardGame("Catan", 3, 4, "strategy"));
        service.addGame(new BoardGame("Pandemic", 2, 4, "coop"));
        service.addGame(new BoardGame("SoloGame", 1, 1, "solo"));

        // Act
        List<BoardGame> result = service.findGamesByPlayers(4);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(g -> g.getTitle().equals("Catan")));
        assertTrue(result.stream().anyMatch(g -> g.getTitle().equals("Pandemic")));
    }

    @Test
    void findGamesByPlayers_shouldReturnGamesSortedAlphabetically() {
        // Arrange
        service.addGame(new BoardGame("7 Wonders", 3, 7, "strategy"));
        service.addGame(new BoardGame("Catan", 3, 4, "strategy"));
        service.addGame(new BoardGame("Pandemic", 2, 4, "coop"));

        // Act
        List<BoardGame> result = service.findGamesByPlayers(4);

        // Assert
        assertEquals("7 Wonders", result.get(0).getTitle());
        assertEquals("Catan", result.get(1).getTitle());
        assertEquals("Pandemic", result.get(2).getTitle());
    }

    @Test
    void findGamesByPlayers_shouldReturnEmptyList_whenNoMatch() {
        // Arrange
        service.addGame(new BoardGame("SoloGame", 1, 1, "solo"));

        // Act
        List<BoardGame> result = service.findGamesByPlayers(5);

        // Assert
        assertTrue(result.isEmpty());
    }
}