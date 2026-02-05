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

    StorageStrategy storage;
    GameService service;

    @BeforeEach
    void setUp() throws IOException {
        storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of());
        service = new GameService(storage);
    }

    @Test
    void addGame_shouldAddGameToListAndSave() throws IOException {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        service.addGame(game);

        // Assert
        assertTrue(service.gameExists("Catan"));
        verify(storage, times(1)).save(service.getAllGames());
    }

    @Test
    void removeGame_shouldRemoveGameFromListAndSave() throws IOException {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        service.addGame(game);

        // Act
        service.removeGame(game);

        // Assert
        assertFalse(service.gameExists("Catan"));
        verify(storage, times(2)).save(anyList()); // add + remove
    }

    @Test
    void getAllGames_shouldReturnCopyNotReference() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        service.addGame(game);

        // Act
        List<BoardGame> games = service.getAllGames();
        games.clear();

        // Assert
        assertEquals(1, service.getAllGames().size()); // original list remains
    }
}