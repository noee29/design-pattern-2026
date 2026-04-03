package fr.fges.ui;

import fr.fges.model.BoardGame;
import fr.fges.service.GameFinder;

import java.util.List;

/** Action pour rechercher les jeux compatibles avec un nombre spécifique de joueurs. */
public class FindGamesByPlayersAction implements MenuEntry {

    private final GameFinder finder;
    private final UserInput input;

    public FindGamesByPlayersAction(GameFinder finder, UserInput input) {
        this.finder = finder;
        this.input = input;
    }

    @Override
    public String getLabel() {
        return "Games for X Players";
    }

    @Override
    public void execute() {
        int count = input.getIntAtLeast("Number of players", 1);

        List<BoardGame> games = finder.findGamesByPlayers(count);

        if (games.isEmpty()) {
            System.out.println("No games found for " + count + " players.");
            return;
        }

        System.out.println("\nGames for " + count + " players:");
        for (BoardGame g : games) {
            System.out.println("- " + g);
        }
    }
}