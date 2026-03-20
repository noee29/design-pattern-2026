package fr.fges.samplecode;

import fr.fges.tournament.ChampionshipStrategy;
import fr.fges.tournament.Player;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChampionshipStrategyTest {

    private ChampionshipStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new ChampionshipStrategy();
    }

    // ── getName ───────────────────────────────────────────────────────────────

    @Test
    void getName_shouldReturnCorrectName() {
        // Arrange — strategy créée dans setUp()

        // Act
        String name = strategy.getName();

        // Assert
        assertEquals("Championship (everyone plays everyone)", name);
    }

    // ── play ──────────────────────────────────────────────────────────────────

    @Test
    void play_shouldNotThrow_withTwoPlayers() {
        // Arrange
        List<Player> players = List.of(new Player("Alice"), new Player("Bob"));
        UserInput input = mock(UserInput.class);
        when(input.getIntBetween(anyString(), anyInt(), anyInt())).thenReturn(1);

        // Act & Assert
        assertDoesNotThrow(() -> strategy.play(players, input));
    }

    @Test
    void play_shouldMakeCorrectNumberOfMatches_forThreePlayers() {
        // Arrange — 3 joueurs → 3 matchs
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        Player charlie = new Player("Charlie");
        List<Player> players = List.of(alice, bob, charlie);
        UserInput input = mock(UserInput.class);
        when(input.getIntBetween(anyString(), anyInt(), anyInt())).thenReturn(1);

        // Act
        strategy.play(players, input);

        // Assert
        verify(input, times(3)).getIntBetween(anyString(), eq(1), eq(2));
    }

    @Test
    void play_shouldMakeCorrectNumberOfMatches_forFourPlayers() {
        // Arrange — 4 joueurs -> 6 matchs
        List<Player> players = List.of(
                new Player("Alice"), new Player("Bob"),
                new Player("Charlie"), new Player("Dave")
        );
        UserInput input = mock(UserInput.class);
        when(input.getIntBetween(anyString(), anyInt(), anyInt())).thenReturn(1);

        // Act
        strategy.play(players, input);

        // Assert
        verify(input, times(6)).getIntBetween(anyString(), eq(1), eq(2));
    }

    @Test
    void play_shouldNotCallInput_withOnePlayer() {
        // Arrange — 1 joueur → aucun match possible
        List<Player> players = List.of(new Player("Alice"));
        UserInput input = mock(UserInput.class);

        // Act
        strategy.play(players, input);

        // Assert
        verify(input, never()).getIntBetween(anyString(), anyInt(), anyInt());
    }

    @Test
    void play_shouldAddWinToWinner_andLossToLoser() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        List<Player> players = List.of(alice, bob);
        UserInput input = mock(UserInput.class);
        when(input.getIntBetween(anyString(), anyInt(), anyInt())).thenReturn(1); // Alice gagne

        // Act
        strategy.play(players, input);

        // Assert
        assertEquals(3, alice.getPoints()); // victoire = 3 pts
        assertEquals(1, bob.getPoints());   // défaite = 1 pt
    }
}