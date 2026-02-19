package fr.fges.tournament;

import java.util.List;

public class KingOfHillStrategy implements TournamentStrategy {

    @Override
    public String getName() {
        return "King of the Hill (winner stays)";
    }

    @Override
    public List<Match> generateMatches(List<Player> players) {
        // Matches are generated dynamically in Tournament based on winner
        // This method is not used for KingOfHill
        return List.of();
    }
}