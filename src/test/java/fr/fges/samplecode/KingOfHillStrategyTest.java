package fr.fges.samplecode;

import fr.fges.tournament.KingOfHillStrategy;
import fr.fges.tournament.Player;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KingOfHillStrategyTest {

    private KingOfHillStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new KingOfHillStrategy();
    }

    // ── getName

    @Test
    void getName_shouldReturnCorrectName() {
        // Arrange — strategy créée dans setUp()

        // Act
        String name = strategy.getName();

        // Assert
        assertEquals("King of the Hill (winner stays)", name);
    }

    // ── play

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
    void play_shouldNotCallInput_whenListIsEmpty() {
        // Arrange
        UserInput input = mock(UserInput.class);

        // Act
        strategy.play(List.of(), input);

        // Assert
        verify(input, never()).getIntBetween(anyString(), anyInt(), anyInt());
    }

    @Test
    void play_shouldMakeExactlyNMinusOneMatches_forNPlayers() {
        // Arrange — 4 joueurs → 3 matchs (chaque joueur affronte le roi)
        List<Player> players = List.of(
                new Player("Alice"), new Player("Bob"),
                new Player("Charlie"), new Player("Dave")
        );
        UserInput input = mock(UserInput.class);
        when(input.getIntBetween(anyString(), anyInt(), anyInt())).thenReturn(1);

        // Act
        strategy.play(players, input);

        // Assert
        verify(input, times(3)).getIntBetween(anyString(), eq(1), eq(2));
    }

    @Test
    void play_shouldGiveWinToWinner_whenKingWins() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        List<Player> players = List.of(alice, bob);
        UserInput input = mock(UserInput.class);
        when(input.getIntBetween(anyString(), anyInt(), anyInt())).thenReturn(1);

        // Act
        strategy.play(players, input);

        // Assert
        assertEquals(3, alice.getPoints()); // victoire
        assertEquals(1, bob.getPoints());   // défaite
    }

    @Test
    void play_shouldGiveWinToChallenger_whenChallengerWins() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        List<Player> players = List.of(alice, bob);
        UserInput input = mock(UserInput.class);
        when(input.getIntBetween(anyString(), anyInt(), anyInt())).thenReturn(2);

        // Act
        strategy.play(players, input);

        // Assert
        assertEquals(1, alice.getPoints()); // défaite
        assertEquals(3, bob.getPoints());   // victoire
    }

    @Test
    void play_shouldAccumulatePoints_overMultipleMatches() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        Player charlie = new Player("Charlie");
        List<Player> players = List.of(alice, bob, charlie);
        UserInput input = mock(UserInput.class);
        // Alice gagne contre Bob (choix 1), puis Alice gagne contre Charlie (choix 1)
        when(input.getIntBetween(anyString(), anyInt(), anyInt())).thenReturn(1);

        // Act
        strategy.play(players, input);

        // Assert — Alice a gagné 2 fois : 6 points
        assertEquals(6, alice.getPoints());
        assertEquals(2, alice.getWins());
    }
}