package fr.fges.samplecode;

import fr.fges.tournament.Player;
import fr.fges.tournament.Tournament;
import fr.fges.tournament.TournamentStrategy;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TournamentTest {

    // ── play

    @Test
    void play_shouldCallPlay_onStrategy() {
        // Arrange
        List<Player> players = List.of(new Player("Alice"), new Player("Bob"));
        TournamentStrategy strategy = mock(TournamentStrategy.class);
        UserInput input = mock(UserInput.class);
        Tournament tournament = new Tournament(players, strategy, input);

        // Act
        tournament.play();

        // Assert
        verify(strategy, times(1)).play(players, input);
    }

    @Test
    void play_shouldPassCorrectPlayersList_toStrategy() {
        // Arrange
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        List<Player> players = List.of(alice, bob);
        TournamentStrategy strategy = mock(TournamentStrategy.class);
        UserInput input = mock(UserInput.class);
        Tournament tournament = new Tournament(players, strategy, input);

        // Act
        tournament.play();

        // Assert
        verify(strategy).play(argThat(list ->
                list.size() == 2
                        && list.get(0).getName().equals("Alice")
                        && list.get(1).getName().equals("Bob")
        ), eq(input));
    }

    @Test
    void play_shouldPassCorrectInput_toStrategy() {
        // Arrange
        List<Player> players = List.of(new Player("Alice"), new Player("Bob"));
        TournamentStrategy strategy = mock(TournamentStrategy.class);
        UserInput input = mock(UserInput.class);
        Tournament tournament = new Tournament(players, strategy, input);

        // Act
        tournament.play();

        // Assert
        verify(strategy).play(anyList(), eq(input));
    }

    @Test
    void play_shouldNotThrow_withTwoPlayers() {
        // Arrange
        List<Player> players = List.of(new Player("Alice"), new Player("Bob"));
        TournamentStrategy strategy = mock(TournamentStrategy.class);
        UserInput input = mock(UserInput.class);
        Tournament tournament = new Tournament(players, strategy, input);

        // Act & Assert
        assertDoesNotThrow(tournament::play);
    }

    @Test
    void play_shouldNotThrow_withThreePlayers() {
        // Arrange
        List<Player> players = List.of(
                new Player("Alice"), new Player("Bob"), new Player("Charlie")
        );
        TournamentStrategy strategy = mock(TournamentStrategy.class);
        UserInput input = mock(UserInput.class);
        Tournament tournament = new Tournament(players, strategy, input);

        // Act & Assert
        assertDoesNotThrow(tournament::play);
    }
}