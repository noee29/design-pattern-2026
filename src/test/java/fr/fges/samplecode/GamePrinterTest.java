package fr.fges.samplecode;

import fr.fges.BoardGame;
import fr.fges.GamePrinter;
import fr.fges.GameRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class GamePrinterTest {

    @Test
    void viewAllGames_shouldHandleEmptyRepository() {
        // Arrange
        GameRepository repository = mock(GameRepository.class);
        when(repository.isEmpty()).thenReturn(true);

        GamePrinter printer = new GamePrinter();

        // Act & Assert
        assertDoesNotThrow(() -> printer.viewAllGames(repository));
        verify(repository, times(1)).isEmpty();
    }

    @Test
    void viewAllGames_shouldDisplayGamesWhenNotEmpty() {
        // Arrange
        GameRepository repository = mock(GameRepository.class);
        when(repository.isEmpty()).thenReturn(false);

        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("Azul", 2, 4, "abstract")
        );

        when(repository.getGamesSortedByTitle()).thenReturn(games);

        GamePrinter printer = new GamePrinter();

        // Act & Assert
        assertDoesNotThrow(() -> printer.viewAllGames(repository));
        verify(repository, times(1)).getGamesSortedByTitle();
    }
}
