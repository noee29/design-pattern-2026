package fr.fges.tournament;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TournamentResult {

    private final List<Player> players;

    public TournamentResult(List<Player> players) {
        this.players = new ArrayList<>(players);
    }

    private static Comparator<Player> rankingComparator() {
        return Comparator.comparingInt(Player::getPoints).reversed()
                .thenComparing(Comparator.comparingInt(Player::getWins).reversed())
                .thenComparing(Player::getName);
    }

    public List<Player> getRankedPlayers() {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(rankingComparator());
        return sorted;
    }

    public void print() {
        System.out.println("\n=== Tournament Results ===");
        List<Player> ranked = getRankedPlayers();
        for (int i = 0; i < ranked.size(); i++) {
            System.out.println((i + 1) + ". " + ranked.get(i));
        }
    }
}
