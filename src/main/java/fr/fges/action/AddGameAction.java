package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;

public class AddGameAction implements MenuAction {

    private final GameService service;
    private final UserInput input;

    public AddGameAction(GameService service, UserInput input) {
        this.service = service;
        this.input = input;
    }

    @Override
    public void execute() {
        String title = input.getString("Enter game title");
        int minPlayers = input.getIntAtLeast("Enter minimum players", 1);
        int maxPlayers = input.getIntAtLeast("Enter maximum players", minPlayers);
        String category = input.getString("Enter game category");

        BoardGame game = new BoardGame(title, minPlayers, maxPlayers, category);
        service.addGame(game);
        System.out.println("Game added.");
    }
}