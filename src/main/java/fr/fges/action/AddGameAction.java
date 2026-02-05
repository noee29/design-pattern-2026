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
        String title = input.getString("Title");

        if (service.gameExists(title)) {
            System.out.println("Error: A game with title \"" + title + "\" already exists in the collection.");
            return;
        }

        int minPlayers = input.getIntAtLeast("Minimum Players", 1);
        int maxPlayers = input.getIntAtLeast("Maximum Players", minPlayers);
        String category = input.getString("Category (e.g., fantasy, cooperative, strategy)");

        BoardGame game = new BoardGame(title, minPlayers, maxPlayers, category);
        service.addGame(game);
        System.out.println("Board game added successfully.");
    }
}