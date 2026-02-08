package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;

import java.util.List;

public class FindGamesByPlayersAction implements MenuAction {

    private final GameService service;
    private final UserInput input;

    public FindGamesByPlayersAction(GameService service, UserInput input) {
        this.service = service;
        this.input = input;
    }

    @Override
    public void execute() {
        int count = input.getIntAtLeast("Number of players", 1);

        List<BoardGame> games = service.findGamesByPlayers(count);

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
