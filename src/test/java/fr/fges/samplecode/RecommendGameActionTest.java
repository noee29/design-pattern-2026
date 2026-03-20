package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameRepository;
import fr.fges.ui.RecommendGameAction;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendGameActionTest {

    private GameRepository repository;
    private UserInput input;
    private RecommendGameAction action;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        repository = new GameRepository();
        input = mock(UserInput.class);
        action = new RecommendGameAction(repository, input);
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
        assertEquals("Recommend Game", label);
    }

    // ── execute — collection vide

    @Test
    void execute_shouldNotThrow_whenCollectionIsEmpty() {
        // Arrange — repository vide

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldNotAskForPlayerCount_whenCollectionIsEmpty() {
        // Arrange — repository vide

        // Act
        action.execute();

        // Assert
        verify(input, never()).getIntAtLeast(anyString(), anyInt());
    }

    @Test
    void execute_shouldPrintNoGamesMessage_whenCollectionIsEmpty() {
        // Arrange — repository vide

        // Act
        action.execute();

        // Assert
        assertTrue(outContent.toString().contains("No games"));
    }

    // ── execute — avec des jeux

    @Test
    void execute_shouldAskForPlayerCount_whenCollectionHasGames() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));
        when(input.getIntAtLeast("How many players?", 1)).thenReturn(4);

        // Act
        action.execute();

        // Assert
        verify(input, times(1)).getIntAtLeast("How many players?", 1);
    }

    @Test
    void execute_shouldNotThrow_whenCompatibleGameExists() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));
        when(input.getIntAtLeast("How many players?", 1)).thenReturn(4);

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldPrintRecommendedGame_whenCompatibleGameExists() {
        // Arrange
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));
        when(input.getIntAtLeast("How many players?", 1)).thenReturn(4);

        // Act
        action.execute();

        // Assert
        assertTrue(outContent.toString().contains("Catan"));
    }

    @Test
    void execute_shouldNotThrow_whenNoCompatibleGame() {
        // Arrange
        repository.add(new BoardGame("SoloGame", 1, 1, "solo"));
        when(input.getIntAtLeast("How many players?", 1)).thenReturn(5);

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldPrintNoCompatibleMessage_whenNoCompatibleGame() {
        // Arrange
        repository.add(new BoardGame("SoloGame", 1, 1, "solo"));
        when(input.getIntAtLeast("How many players?", 1)).thenReturn(5);

        // Act
        action.execute();

        // Assert
        assertTrue(outContent.toString().contains("No games available"));
    }

    @Test
    void execute_shouldOnlyRecommendCompatibleGame_notOutOfRangeGame() {
        // Arrange — seul Catan est compatible pour 4 joueurs
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));
        repository.add(new BoardGame("SoloGame", 1, 1, "solo"));
        when(input.getIntAtLeast("How many players?", 1)).thenReturn(4);

        // Act
        action.execute();

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Catan"));
        assertFalse(output.contains("SoloGame"));
    }
}