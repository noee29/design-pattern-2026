package fr.fges.tournament;

import java.util.List;

public interface TournamentStrategy {
    List<Match> generateMatches(List<Player> players);
    String getName();
}