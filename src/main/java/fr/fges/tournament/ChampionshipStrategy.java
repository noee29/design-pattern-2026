package fr.fges.tournament;

import java.util.ArrayList;
import java.util.List;

public class ChampionshipStrategy implements TournamentStrategy {

    @Override
    public String getName() {
        return "Championship (everyone plays everyone)";
    }

    @Override
    public List<Match> generateMatches(List<Player> players) {
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                matches.add(new Match(players.get(i), players.get(j)));
            }
        }
        return matches;
    }
}