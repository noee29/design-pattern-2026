package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameFinder;
import fr.fges.service.GameRepository;
import fr.fges.ui.FindGamesByPlayersAction;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindGamesByPlayersActionTest {

    private GameFinder finder;
    private UserInput input;
    private FindGamesByPlayersAction action;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        finder = mock(GameFinder.class);
        input = mock(UserInput.class);
        action = new FindGamesByPlayersAction(finder, input);
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
        assertEquals("Games for X Players", label);
    }

    // ── execute

    @Test
    void execute_shouldCallGetIntAtLeast_toAskPlayerCount() {
        // Arrange
        when(input.getIntAtLeast("Number of players", 1)).thenReturn(4);
        when(finder.findGamesByPlayers(4)).thenReturn(List.of());

        // Act
        action.execute();

        // Assert
        verify(input, times(1)).getIntAtLeast("Number of players", 1);
    }

    @Test
    void execute_shouldCallFindGamesByPlayers_withEnteredCount() {
        // Arrange
        when(input.getIntAtLeast("Number of players", 1)).thenReturn(4);
        when(finder.findGamesByPlayers(4)).thenReturn(List.of());

        // Act
        action.execute();

        // Assert
        verify(finder, times(1)).findGamesByPlayers(4);
    }

    @Test
    void execute_shouldNotThrow_whenGamesAreFound() {
        // Arrange
        when(input.getIntAtLeast("Number of players", 1)).thenReturn(4);
        when(finder.findGamesByPlayers(4)).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy")
        ));

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldNotThrow_whenNoGamesFound() {
        // Arrange
        when(input.getIntAtLeast("Number of players", 1)).thenReturn(99);
        when(finder.findGamesByPlayers(99)).thenReturn(List.of());

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldPrintNoGamesMessage_whenNoGamesFound() {
        // Arrange
        when(input.getIntAtLeast("Number of players", 1)).thenReturn(99);
        when(finder.findGamesByPlayers(99)).thenReturn(List.of());

        // Act
        action.execute();

        // Assert
        assertTrue(outContent.toString().contains("No games found"));
    }

    @Test
    void execute_shouldPrintGameTitle_whenOneGameFound() {
        // Arrange
        when(input.getIntAtLeast("Number of players", 1)).thenReturn(4);
        when(finder.findGamesByPlayers(4)).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy")
        ));

        // Act
        action.execute();

        // Assert
        assertTrue(outContent.toString().contains("Catan"));
    }

    @Test
    void execute_shouldPrintAllGameTitles_whenMultipleGamesFound() {
        // Arrange
        when(input.getIntAtLeast("Number of players", 1)).thenReturn(4);
        when(finder.findGamesByPlayers(4)).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("Pandemic", 2, 4, "coop")
        ));

        // Act
        action.execute();

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Catan"));
        assertTrue(output.contains("Pandemic"));
    }

    // ── intégration avec vrai GameFinder

    @Test
    void execute_withRealFinder_shouldOnlyShowCompatibleGames() {
        // Arrange
        GameRepository repository = new GameRepository();
        repository.add(new BoardGame("Catan", 3, 4, "strategy"));
        repository.add(new BoardGame("SoloGame", 1, 1, "solo"));
        repository.add(new BoardGame("7 Wonders", 3, 7, "strategy"));
        GameFinder realFinder = new GameFinder(repository);
        UserInput realInput = mock(UserInput.class);
        when(realInput.getIntAtLeast("Number of players", 1)).thenReturn(4);
        FindGamesByPlayersAction realAction = new FindGamesByPlayersAction(realFinder, realInput);

        // Act
        realAction.execute();

        // Assert — SoloGame ne doit pas apparaître (max 1 joueur)
        String output = outContent.toString();
        assertTrue(output.contains("Catan"));
        assertFalse(output.contains("SoloGame"));
    }
}