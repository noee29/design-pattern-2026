package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;
import fr.fges.history.ActionHistory;

public class AddGameAction implements UndoableAction {

    private final GameService service;
    private final UserInput input;
    private final ActionHistory history;
    private BoardGame lastAdded;

    public AddGameAction(GameService service, UserInput input, ActionHistory history) {
        this.service = service;
        this.input = input;
        this.history = history;
    }

    @Override
    public void execute() {
        String title = input.getString("Title: ");
        int minPlayers = input.getInt("Minimum Players: ");

        int maxPlayers;
        do {
            maxPlayers = input.getInt("Maximum Players: ");
            if (maxPlayers < minPlayers) {
                System.out.println("Maximum Players must be at least " + minPlayers + ".");
            }
        } while (maxPlayers < minPlayers);

        String category = input.getString("Category (e.g., fantasy, strategy): ");

        lastAdded = new BoardGame(title, minPlayers, maxPlayers, category);
        service.addGame(lastAdded);
        history.push(this);

        System.out.println("Board game added successfully.");
    }

    @Override
    public void undo() {
        if (lastAdded != null) {
            service.removeGame(lastAdded);
            System.out.println("Undone: Removed \"" + lastAdded.getTitle() + "\" from collection");
        }
    }
}