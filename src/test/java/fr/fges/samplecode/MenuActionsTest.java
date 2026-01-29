package fr.fges.samplecode;

import fr.fges.BoardGame;
import fr.fges.GameRepository;
import fr.fges.MenuActions;
import fr.fges.StorageStrategy;
import fr.fges.UserInput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for MenuActions class
 * using Arrange–Act–Assert pattern and Mockito mocks.
 */
class MenuActionsTest {

    @Test
    void listAllGames_shouldNotCrashWithEmptyCollection() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of());

        GameRepository repository = new GameRepository(storage);
        UserInput input = mock(UserInput.class);
        MenuActions actions = new MenuActions(input, repository);

        // Act & Assert
        assertDoesNotThrow(actions::listAllGames);
    }

    @Test
    void listAllGames_shouldNotCrashWithGames() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy")
        ));

        GameRepository repository = new GameRepository(storage);
        UserInput input = mock(UserInput.class);
        MenuActions actions = new MenuActions(input, repository);

        // Act & Assert
        assertDoesNotThrow(actions::listAllGames);
    }

    @Test
    void addGame_shouldAddOneGame() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of());

        UserInput input = mock(UserInput.class);
        when(input.getString("Title")).thenReturn("Catan");
        when(input.getIntAtLeast("Minimum Players", 1)).thenReturn(3);
        when(input.getIntAtLeastOther("Maximum Players", 3)).thenReturn(4);
        when(input.getString("Category (e.g., fantasy, cooperative, family, strategy)"))
                .thenReturn("strategy");

        GameRepository repository = new GameRepository(storage);
        MenuActions actions = new MenuActions(input, repository);

        // Act
        actions.addGame();

        // Assert
        verify(storage, times(1)).save(repository.getGames());
        assertEquals(1, repository.getGames().size());
        assertEquals("Catan", repository.getGames().get(0).title());
    }

    @Test
    void removeGame_shouldRemoveExistingGame() throws Exception {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of(game));

        UserInput input = mock(UserInput.class);
        when(input.getString("Title of game to remove")).thenReturn("Catan");

        GameRepository repository = new GameRepository(storage);
        MenuActions actions = new MenuActions(input, repository);

        // Act
        actions.removeGame();

        // Assert
        assertTrue(repository.getGames().isEmpty());
        verify(storage, times(1)).save(repository.getGames());
    }

    @Test
    void removeGame_shouldNotCrashWhenGameDoesNotExist() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(List.of());

        UserInput input = mock(UserInput.class);
        when(input.getString("Title of game to remove")).thenReturn("Unknown");

        GameRepository repository = new GameRepository(storage);
        MenuActions actions = new MenuActions(input, repository);

        // Act & Assert
        assertDoesNotThrow(actions::removeGame);
    }
}
