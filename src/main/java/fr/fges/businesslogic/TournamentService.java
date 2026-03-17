package fr.fges.businesslogic;

import fr.fges.tournament.Player;
import fr.fges.tournament.Tournament;
import fr.fges.tournament.TournamentStrategy;
import fr.fges.ui.UserInput;

import java.util.List;

public class TournamentService {

    public void startTournament(List<Player> players, TournamentStrategy strategy, UserInput input) {
        if (players == null || players.size() < 2) {
            System.out.println("Not enough players to start a tournament.");
            return;
        }

        System.out.println("\nStarting tournament with strategy: " + strategy.getName());

        Tournament tournament = new Tournament(players, strategy, input);
        tournament.play();
    }
}