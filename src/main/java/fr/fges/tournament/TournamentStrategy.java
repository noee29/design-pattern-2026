package fr.fges.tournament;

import fr.fges.ui.UserInput;

import java.util.List;

/** Interface pour les stratégies de tournoi : définit les différents formats (Championship, King of the Hill). */
public interface TournamentStrategy {
    String getName();
    void play(List<Player> players, UserInput input);
}