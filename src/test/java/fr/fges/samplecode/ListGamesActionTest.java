package fr.fges.samplecode;

import fr.fges.model.BoardGame;
import fr.fges.service.GameRepository;
import fr.fges.ui.GamePrinter;
import fr.fges.ui.ListGamesAction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListGamesActionTest {

    private GameRepository repository;
    private GamePrinter printer;
    private ListGamesAction action;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        repository = mock(GameRepository.class);
        printer = mock(GamePrinter.class);
        action = new ListGamesAction(repository, printer);
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
        assertEquals("List All Board Games", label);
    }

    // ── execute

    @Test
    void execute_shouldCallFindAll_onRepository() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        action.execute();

        // Assert
        verify(repository, times(1)).findAll();
    }

    @Test
    void execute_shouldCallPrintGames_onPrinter() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(
                new BoardGame("Catan", 3, 4, "strategy")
        ));

        // Act
        action.execute();

        // Assert
        verify(printer, times(1)).printGames(anyList());
    }

    @Test
    void execute_shouldPassAllGamesToPrinter() {
        // Arrange
        List<BoardGame> games = List.of(
                new BoardGame("Catan", 3, 4, "strategy"),
                new BoardGame("Pandemic", 2, 4, "coop")
        );
        when(repository.findAll()).thenReturn(games);

        // Act
        action.execute();

        // Assert
        verify(printer).printGames(games);
    }

    @Test
    void execute_shouldPassEmptyList_whenRepositoryIsEmpty() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        action.execute();

        // Assert
        verify(printer).printGames(argThat(List::isEmpty));
    }

    @Test
    void execute_shouldNotThrow_whenRepositoryIsEmpty() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    // ── intégration avec vrai GameRepository et GamePrinter

    @Test
    void execute_withRealDependencies_shouldPrintAllGameTitles() {
        // Arrange
        GameRepository realRepo = new GameRepository();
        realRepo.add(new BoardGame("Catan", 3, 4, "strategy"));
        realRepo.add(new BoardGame("Pandemic", 2, 4, "coop"));
        GamePrinter realPrinter = new GamePrinter();
        ListGamesAction realAction = new ListGamesAction(realRepo, realPrinter);

        // Act
        realAction.execute();

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("Catan"));
        assertTrue(output.contains("Pandemic"));
    }

    @Test
    void execute_withRealDependencies_shouldPrintNoGamesMessage_whenEmpty() {
        // Arrange
        GameRepository realRepo = new GameRepository();
        GamePrinter realPrinter = new GamePrinter();
        ListGamesAction realAction = new ListGamesAction(realRepo, realPrinter);

        // Act
        realAction.execute();

        // Assert
        assertTrue(outContent.toString().contains("No games"));
    }
}