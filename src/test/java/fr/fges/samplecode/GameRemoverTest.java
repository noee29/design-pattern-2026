package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameRemover;
import fr.fges.service.GameRepository;
import fr.fges.service.GameSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameRemoverTest {

    private GameRepository repository;
    private GameSaver saver;
    private GameRemover remover;

    @BeforeEach
    void setUp() {
        repository = new GameRepository();
        saver = mock(GameSaver.class);
        remover = new GameRemover(repository, saver);
    }

    // ── removeGame

    @Test
    void removeGame_shouldRemoveGameFromRepository() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        repository.add(game);

        // Act
        remover.removeGame(game);

        // Assert
        assertFalse(repository.getGames().contains(game));
    }

    @Test
    void removeGame_shouldCallSave() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        repository.add(game);

        // Act
        remover.removeGame(game);

        // Assert
        verify(saver, times(1)).save(anyList());
    }

    @Test
    void removeGame_shouldSaveWithoutTheRemovedGame() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");
        repository.add(game);

        // Act
        remover.removeGame(game);

        // Assert
        verify(saver).save(argThat(list -> !list.contains(game)));
    }

    @Test
    void removeGame_shouldOnlyRemoveTargetGame() {
        // Arrange
        BoardGame catan = new BoardGame("Catan", 3, 4, "strategy");
        BoardGame pandemic = new BoardGame("Pandemic", 2, 4, "coop");
        repository.add(catan);
        repository.add(pandemic);

        // Act
        remover.removeGame(catan);

        // Assert
        assertTrue(repository.getGames().contains(pandemic));
        assertFalse(repository.getGames().contains(catan));
    }
}