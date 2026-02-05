package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.ui.GamePrinter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GamePrinterTest {

    @Test
    void printGames_shouldNotCrashWithEmptyList() {
        // Arrange
        GamePrinter printer = new GamePrinter();

        // Act & Assert
        assertDoesNotThrow(() -> printer.printGames(List.of()));
    }

    @Test
    void printGames_shouldNotCrashWithGames() {
        // Arrange
        GamePrinter printer = new GamePrinter();
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "Strategy")
        );

        // Act & Assert
        assertDoesNotThrow(() -> printer.printGames(games));
    }
}