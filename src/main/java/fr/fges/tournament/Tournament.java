package fr.fges.tournament;

import fr.fges.ui.UserInput;

import java.util.List;

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
        strategy.play(players, input);
        new TournamentResult(players).print();
    }
}