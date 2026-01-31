package fr.fges.samplecode;

import fr.fges.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;

class MenuActionsTest {

    private UserInput input;
    private GameRepository repository;
    private GamePrinter printer;
    private Random random;
    private DayPolicy dayPolicy;

    private MenuActions actions;

    @BeforeEach
    void setUp() {
        // Arrange
        input = mock(UserInput.class);
        repository = mock(GameRepository.class);
        printer = mock(GamePrinter.class);
        random = mock(Random.class);
        dayPolicy = mock(DayPolicy.class);

        actions = new MenuActions(input, repository, printer, random, dayPolicy);
    }

    @Test
    void recommendGame_shouldSelectRandomGame_whenCompatibleGameExists() {
        // Arrange
        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");

        when(input.getIntAtLeast(anyString(), anyInt())).thenReturn(4);
        when(repository.findCompatibleGames(4)).thenReturn(List.of(game));
        when(random.nextInt(1)).thenReturn(0);

        // Act
        actions.recommendGame();

        // Assert
        verify(repository).findCompatibleGames(4);
        verify(random).nextInt(1);
    }

    @Test
    void recommendGame_shouldNotUseRandom_whenNoCompatibleGame() {
        // Arrange
        when(input.getIntAtLeast(anyString(), anyInt())).thenReturn(5);
        when(repository.findCompatibleGames(5)).thenReturn(List.of());

        // Act
        actions.recommendGame();

        // Assert
        verify(repository).findCompatibleGames(5);
        verifyNoInteractions(random);
    }

    @Test
    void weekendSummary_shouldStop_whenNotWeekend() {
        // Arrange
        when(dayPolicy.isWeekend()).thenReturn(false);

        // Act
        actions.weekendSummary();

        // Assert
        verify(dayPolicy).isWeekend();
        verifyNoInteractions(repository);
    }

    @Test
    void weekendSummary_shouldDisplayRandomGame_whenWeekendAndGamesExist() {
        // Arrange
        when(dayPolicy.isWeekend()).thenReturn(true);
        when(repository.isEmpty()).thenReturn(false);

        BoardGame game = new BoardGame("Uno", 2, 8, "Family");
        when(repository.getGames()).thenReturn(List.of(game));
        when(random.nextInt(1)).thenReturn(0);

        // Act
        actions.weekendSummary();

        // Assert
        verify(repository).getGames();
        verify(random).nextInt(1);
    }
}
