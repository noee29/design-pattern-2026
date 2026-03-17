package fr.fges.tournament;

import fr.fges.ui.UserInput;

import java.util.List;

public interface TournamentStrategy {
    String getName();
    void play(List<Player> players, UserInput input);
}