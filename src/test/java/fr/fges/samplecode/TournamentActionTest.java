package fr.fges.samplecode;

import fr.fges.businesslogic.TournamentService;
import fr.fges.model.BoardGame;
import fr.fges.service.GameRepository;
import fr.fges.tournament.Player;
import fr.fges.tournament.TournamentStrategy;
import fr.fges.ui.TournamentAction;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TournamentActionTest {

    private GameRepository repository;
    private UserInput input;
    private TournamentService tournamentService;
    private TournamentAction action;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        repository = new GameRepository();
        input = mock(UserInput.class);
        tournamentService = mock(TournamentService.class);
        action = new TournamentAction(repository, input, tournamentService);
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
        assertEquals("Tournament Mode", label);
    }

    // ── execute — aucun jeu 2 joueurs disponible

    @Test
    void execute_shouldNotThrow_whenNoTwoPlayerGameAvailable() {
        // Arrange — repository vide, pas de jeu compatible 2 joueurs

        // Act & Assert
        assertDoesNotThrow(action::execute);
    }

    @Test
    void execute_shouldPrintNoGamesMessage_whenNoTwoPlayerGameAvailable() {
        // Arrange — seul un jeu solo, incompatible
        repository.add(new BoardGame("SoloGame", 1, 1, "solo"));

        // Act
        action.execute();

        // Assert
        assertTrue(outContent.toString().contains("No 2-player games available"));
    }

    @Test
    void execute_shouldNotCallTournamentService_whenNoTwoPlayerGameAvailable() {
        // Arrange — repository vide

        // Act
        action.execute();

        // Assert
        verify(tournamentService, never()).startTournament(anyList(), any(), any());
    }

    // ── execute — jeu 2 joueurs disponible

    @Test
    void execute_shouldCallStartTournament_whenTwoPlayerGameExists() {
        // Arrange
        repository.add(new BoardGame("Chess", 2, 2, "strategy"));
        // Sélectionner le jeu n°1
        when(input.getIntBetween("Select game", 1, 1)).thenReturn(1);
        // 3 participants minimum
        when(input.getIntBetween("Number of participants", 3, 8)).thenReturn(3);
        // Noms des joueurs
        when(input.getString("Enter player 1 name: ")).thenReturn("Alice");
        when(input.getString("Enter player 2 name: ")).thenReturn("Bob");
        when(input.getString("Enter player 3 name: ")).thenReturn("Charlie");
        // Choisir Championship
        when(input.getIntBetween("Select format", 1, 2)).thenReturn(1);

        // Act
        action.execute();

        // Assert
        verify(tournamentService, times(1)).startTournament(anyList(), any(TournamentStrategy.class), eq(input));
    }

    @Test
    void execute_shouldCollectCorrectNumberOfPlayers_whenThreeParticipants() {
        // Arrange
        repository.add(new BoardGame("Chess", 2, 2, "strategy"));
        when(input.getIntBetween("Select game", 1, 1)).thenReturn(1);
        when(input.getIntBetween("Number of participants", 3, 8)).thenReturn(3);
        when(input.getString("Enter player 1 name: ")).thenReturn("Alice");
        when(input.getString("Enter player 2 name: ")).thenReturn("Bob");
        when(input.getString("Enter player 3 name: ")).thenReturn("Charlie");
        when(input.getIntBetween("Select format", 1, 2)).thenReturn(1);

        // Act
        action.execute();

        // Assert — startTournament doit recevoir une liste de 3 joueurs
        verify(tournamentService).startTournament(
                argThat(list -> list.size() == 3),
                any(),
                any()
        );
    }

    @Test
    void execute_shouldFilterOnlyTwoPlayerCompatibleGames() {
        // Arrange — seulement Chess est compatible 2 joueurs
        repository.add(new BoardGame("Chess", 2, 2, "strategy"));
        repository.add(new BoardGame("BigGame", 5, 10, "party")); // non compatible
        when(input.getIntBetween("Select game", 1, 1)).thenReturn(1);
        when(input.getIntBetween("Number of participants", 3, 8)).thenReturn(3);
        when(input.getString("Enter player 1 name: ")).thenReturn("Alice");
        when(input.getString("Enter player 2 name: ")).thenReturn("Bob");
        when(input.getString("Enter player 3 name: ")).thenReturn("Charlie");
        when(input.getIntBetween("Select format", 1, 2)).thenReturn(1);

        // Act
        action.execute();

        // Assert — le menu de sélection de jeu n'a proposé que 1 jeu (max = 1)
        verify(input).getIntBetween("Select game", 1, 1);
    }

    @Test
    void execute_shouldUseChampionshipStrategy_whenFormatOneSelected() {
        // Arrange
        repository.add(new BoardGame("Chess", 2, 2, "strategy"));
        when(input.getIntBetween("Select game", 1, 1)).thenReturn(1);
        when(input.getIntBetween("Number of participants", 3, 8)).thenReturn(3);
        when(input.getString("Enter player 1 name: ")).thenReturn("Alice");
        when(input.getString("Enter player 2 name: ")).thenReturn("Bob");
        when(input.getString("Enter player 3 name: ")).thenReturn("Charlie");
        when(input.getIntBetween("Select format", 1, 2)).thenReturn(1); // Championship

        // Act
        action.execute();

        // Assert — la stratégie passée doit être Championship
        verify(tournamentService).startTournament(
                anyList(),
                argThat(s -> s.getName().contains("Championship")),
                any()
        );
    }

    @Test
    void execute_shouldUseKingOfHillStrategy_whenFormatTwoSelected() {
        // Arrange
        repository.add(new BoardGame("Chess", 2, 2, "strategy"));
        when(input.getIntBetween("Select game", 1, 1)).thenReturn(1);
        when(input.getIntBetween("Number of participants", 3, 8)).thenReturn(3);
        when(input.getString("Enter player 1 name: ")).thenReturn("Alice");
        when(input.getString("Enter player 2 name: ")).thenReturn("Bob");
        when(input.getString("Enter player 3 name: ")).thenReturn("Charlie");
        when(input.getIntBetween("Select format", 1, 2)).thenReturn(2); // King of the Hill

        // Act
        action.execute();

        // Assert
        verify(tournamentService).startTournament(
                anyList(),
                argThat(s -> s.getName().contains("King")),
                any()
        );
    }
}