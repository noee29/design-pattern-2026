package fr.fges.tournament;

import fr.fges.ui.UserInput;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tournament {

    private final List<Player> players;
    private final TournamentStrategy strategy;
    private final UserInput input;

    public Tournament(List<Player> players, TournamentStrategy strategy, UserInput input) {
        this.players = players;
        this.strategy = strategy;
        this.input = input;
    }

    public void play() {
        if (strategy instanceof KingOfHillStrategy) {
            playKingOfHill();
        } else {
            playWithGeneratedMatches();
        }
        new TournamentResult(players).print();
    }

    private void playWithGeneratedMatches() {
        List<Match> matches = strategy.generateMatches(players);
        int total = matches.size();
        for (int i = 0; i < matches.size(); i++) {
            playMatch(matches.get(i), i + 1, total);
        }
    }

    private void playKingOfHill() {
        Queue<Player> queue = new LinkedList<>(players);
        Player king = queue.poll();
        int matchNumber = 1;
        int total = players.size() - 1;

        while (!queue.isEmpty()) {
            Player challenger = queue.poll();
            Match match = new Match(king, challenger);
            king = playMatch(match, matchNumber++, total); // winner stays as king
        }
    }

    private Player playMatch(Match match, int matchNumber, int total) {
        System.out.println("\n=== Match " + matchNumber + "/" + total + " ===");
        System.out.println(match.getPlayer1().getName() + " vs " + match.getPlayer2().getName());

        int choice = input.getIntBetween(
                "Winner (1=" + match.getPlayer1().getName() + ", 2=" + match.getPlayer2().getName() + ")",
                1, 2
        );

        Player winner = (choice == 1) ? match.getPlayer1() : match.getPlayer2();
        Player loser  = (choice == 1) ? match.getPlayer2() : match.getPlayer1();

        winner.addWin();
        loser.addLoss();

        return winner;
    }
}