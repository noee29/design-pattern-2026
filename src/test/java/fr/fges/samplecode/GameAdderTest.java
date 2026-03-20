package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameAdder;
import fr.fges.service.GameRepository;
import fr.fges.service.GameSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameAdderTest {

    private GameRepository repository;
    private GameSaver saver;
    private GameAdder adder;

    @BeforeEach
    void setUp() {
        repository = new GameRepository();
        saver = mock(GameSaver.class);
        adder = new GameAdder(repository, saver);
    }

    // ── addGame

    @Test
    void addGame_shouldAddGameToRepository() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        adder.addGame(game);

        // Assert
        assertTrue(repository.getGames().contains(game));
    }

    @Test
    void addGame_shouldCallSave() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        adder.addGame(game);

        // Assert
        verify(saver, times(1)).save(anyList());
    }

    @Test
    void addGame_shouldSaveWithCorrectList() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "strategy");

        // Act
        adder.addGame(game);

        // Assert
        verify(saver).save(argThat(list -> list.contains(game)));
    }

    @Test
    void addGame_calledTwice_shouldSaveTwice() {
        // Arrange
        BoardGame g1 = new BoardGame("Catan", 3, 4, "strategy");
        BoardGame g2 = new BoardGame("Pandemic", 2, 4, "coop");

        // Act
        adder.addGame(g1);
        adder.addGame(g2);

        // Assert
        verify(saver, times(2)).save(anyList());
    }
}