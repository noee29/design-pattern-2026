package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.ui.GamePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GamePrinterTest {

    private GamePrinter printer;

    @BeforeEach
    void setUp() {
        printer = new GamePrinter();
    }

    @Test
    void printGames_shouldNotThrow_whenListIsEmpty() {
        // Arrange
        List<BoardGame> games = List.of();

        // Act & Assert
        assertDoesNotThrow(() -> printer.printGames(games));
    }

    @Test
    void printGames_shouldNotThrow_whenListHasOneGame() {
        // Arrange
        List<BoardGame> games = List.of(new BoardGame("Catan", 3, 4, "Strategy"));

        // Act & Assert
        assertDoesNotThrow(() -> printer.printGames(games));
    }

    @Test
    void printGames_shouldNotThrow_whenListHasMultipleGames() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "Strategy"),
                new BoardGame("Pandemic", 2, 4, "Coop"),
                new BoardGame("7 Wonders", 3, 7, "Strategy")
        );

        // Act & Assert
        assertDoesNotThrow(() -> printer.printGames(games));
    }
}