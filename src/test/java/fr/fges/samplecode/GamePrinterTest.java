package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.ui.GamePrinter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GamePrinterTest {

    private GamePrinter printer;

    // Capture de la sortie console
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        printer = new GamePrinter();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    // ── printGames — liste vide

    @Test
    void printGames_shouldNotThrow_whenListIsEmpty() {
        // Arrange
        List<BoardGame> games = List.of();

        // Act & Assert
        assertDoesNotThrow(() -> printer.printGames(games));
    }

    @Test
    void printGames_shouldPrintNoGamesMessage_whenListIsEmpty() {
        // Arrange
        List<BoardGame> games = List.of();

        // Act
        printer.printGames(games);

        // Assert
        assertTrue(outContent.toString().contains("No games"));
    }

    // ── printGames — liste non vide

    @Test
    void printGames_shouldNotThrow_whenListHasOneGame() {
        // Arrange
        List<BoardGame> games = List.of(new BoardGame("Catan", 3, 4, "Strategy"));

        // Act & Assert
        assertDoesNotThrow(() -> printer.printGames(games));
    }

    @Test
    void printGames_shouldPrintGameTitle_whenListHasOneGame() {
        // Arrange
        List<BoardGame> games = List.of(new BoardGame("Catan", 3, 4, "Strategy"));

        // Act
        printer.printGames(games);

        // Assert
        assertTrue(outContent.toString().contains("Catan"));
    }

    @Test
    void printGames_shouldPrintAllTitles_whenListHasMultipleGames() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "Strategy"),
                new BoardGame("Pandemic", 2, 4, "Coop"),
                new BoardGame("7 Wonders", 3, 7, "Strategy")
        );

        // Act
        printer.printGames(games);

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Catan"));
        assertTrue(output.contains("Pandemic"));
        assertTrue(output.contains("7 Wonders"));
    }

    @Test
    void printGames_shouldPrintTotalCount_whenListHasMultipleGames() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "Strategy"),
                new BoardGame("Pandemic", 2, 4, "Coop")
        );

        // Act
        printer.printGames(games);

        // Assert
        assertTrue(outContent.toString().contains("2"));
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