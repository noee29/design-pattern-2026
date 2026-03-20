package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameRepository;
import fr.fges.ui.WeekendSummaryAction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class WeekendSummaryActionTest {

    private GameRepository repository;
    private WeekendSummaryAction action;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        repository = new GameRepository();
        action = new WeekendSummaryAction(repository);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    // ── getLabel

    @Test
    void getLabel_shouldReturnCorrectLabel() {
        // Arrange — action créée dans setUp()

        // Act
        String label = action.getLabel();

        // Assert
        assertEquals("View Summary (Weekend Special!)", label);
    }

    // ── execute — collection vide

    @Test
    void execute_shouldNotThrow_whenCollectionIsEmpty() {
        // Arrange — repository vide

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldPrintNoGamesMessage_whenCollectionIsEmpty() {
        // Arrange — repository vide

        // Act
        action.execute();

        // Assert
        assertTrue(outContent.toString().contains("No games"));
    }

    // ── execute — collection non vide

    @Test
    void execute_shouldNotThrow_whenCollectionHasOneGame() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldNotThrow_whenCollectionHasExactlyThreeGames() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));
        repository.add(new BoardGame("Pandemic", 2, 4, "coop"));
        repository.add(new BoardGame("7 Wonders", 3, 7, "strategy"));

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldNotThrow_whenCollectionHasMoreThanThreeGames() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));
        repository.add(new BoardGame("Pandemic", 2, 4, "coop"));
        repository.add(new BoardGame("7 Wonders", 3, 7, "strategy"));
        repository.add(new BoardGame("Bingo", 2, 6, "family"));

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldDisplayAtMostThreeGames_whenCollectionHasFiveGames() {
        // Arrange — 5 jeux en collection
        repository.add(new BoardGame("Alpha", 2, 4, "strategy"));
        repository.add(new BoardGame("Beta", 2, 4, "strategy"));
        repository.add(new BoardGame("Gamma", 2, 4, "strategy"));
        repository.add(new BoardGame("Delta", 2, 4, "strategy"));
        repository.add(new BoardGame("Epsilon", 2, 4, "strategy"));

        // Act
        action.execute();

        // Assert — compter les occurrences de "- " (chaque ligne de jeu commence par "- ")
        String output = outContent.toString();
        long lineCount = output.lines()
                .filter(line -> line.trim().startsWith("-"))
                .count();
        assertTrue(lineCount <= 3, "Au maximum 3 jeux doivent être affichés, trouvé : " + lineCount);
    }

    @Test
    void execute_shouldDisplayAllGames_whenCollectionHasFewerThanThreeGames() {
        // Arrange — seulement 2 jeux
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));
        repository.add(new BoardGame("Pandemic", 2, 4, "coop"));

        // Act
        action.execute();

        // Assert — les 2 jeux doivent apparaître
        String output = outContent.toString();
        assertTrue(output.contains("Catan"));
        assertTrue(output.contains("Pandemic"));
    }
}