package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;

public class ListGamesAction implements MenuAction {

    private final GameService service;

    public ListGamesAction(GameService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        if (service.getAllGames().isEmpty()) {
            System.out.println("No games in collection.");
            return;
        }

        for (BoardGame game : service.getAllGames()) {
            System.out.println(game);
        }
    }
}