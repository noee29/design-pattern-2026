package fr.fges.tournament;

import fr.fges.ui.UserInput;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class KingOfHillStrategy implements TournamentStrategy {

    @Override
    public String getName() {
        return "King of the Hill (winner stays)";
    }

    @Override
    public void play(List<Player> players, UserInput input) {
        Queue<Player> queue = new LinkedList<>(players);
        Player king = queue.poll();

        if (king == null) {
            return;
        }

        int matchNumber = 1;
        int total = players.size() - 1;

        System.out.println("\n=== King of the Hill ===");
        System.out.println("Initial king: " + king.getName());

        while (!queue.isEmpty()) {
            Player challenger = queue.poll();

            System.out.println("\n=== Match " + matchNumber + "/" + total + " ===");
            System.out.println("King: " + king.getName());
            System.out.println("Challenger: " + challenger.getName());

            int choice = input.getIntBetween(
                    "Winner (1=" + king.getName() + ", 2=" + challenger.getName() + ")",
                    1, 2
            );

            Player winner = (choice == 1) ? king : challenger;
            Player loser = (choice == 1) ? challenger : king;

            winner.addWin();
            loser.addLoss();

            king = winner;

            System.out.println("New king: " + king.getName());
            matchNumber++;
        }

        System.out.println("\nFinal king: " + king.getName());
    }
}