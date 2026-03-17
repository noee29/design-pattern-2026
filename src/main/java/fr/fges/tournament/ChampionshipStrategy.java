package fr.fges.tournament;

import fr.fges.ui.UserInput;

import java.util.ArrayList;
import java.util.List;

public class ChampionshipStrategy implements TournamentStrategy {

    @Override
    public String getName() {
        return "Championship (everyone plays everyone)";
    }

    @Override
    public void play(List<Player> players, UserInput input) {
        List<Match> matches = generateMatches(players);
        int total = matches.size();

        for (int i = 0; i < matches.size(); i++) {
            playMatch(matches.get(i), i + 1, total, input);
        }
    }

    private List<Match> generateMatches(List<Player> players) {
        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                matches.add(new Match(players.get(i), players.get(j)));
            }
        }

        return matches;
    }

    private void playMatch(Match match, int matchNumber, int total, UserInput input) {
        System.out.println("\n=== Match " + matchNumber + "/" + total + " ===");
        System.out.println(match.getPlayer1().getName() + " vs " + match.getPlayer2().getName());

        int choice = input.getIntBetween(
                "Winner (1=" + match.getPlayer1().getName() + ", 2=" + match.getPlayer2().getName() + ")",
                1, 2
        );

        Player winner = (choice == 1) ? match.getPlayer1() : match.getPlayer2();
        Player loser = (choice == 1) ? match.getPlayer2() : match.getPlayer1();

        winner.addWin();
        loser.addLoss();
    }
}