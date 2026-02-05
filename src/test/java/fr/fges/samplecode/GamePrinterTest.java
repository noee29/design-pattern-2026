package fr.fges.samplecode;

import org.junit.jupiter.api.Test;
import fr.fges.ui.GamePrinter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GamePrinterTest {

    @Test
    void viewAllGames_shouldPrintMessage_whenRepositoryIsEmpty() {
        // Arrange
        GameRepository repository = mock(GameRepository.class);
        when(repository.isEmpty()).thenReturn(true);

        GamePrinter printer = new GamePrinter();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Act
        printer.viewAllGames(repository);

        // Assert
        assertTrue(out.toString().contains("No board games in collection."));
    }
}
