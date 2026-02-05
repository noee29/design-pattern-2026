package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;
import fr.fges.history.ActionHistory;

import java.util.List;

public class RemoveGameAction implements UndoableAction {

    private final GameService service;
    private final UserInput input;
    private final ActionHistory history;
    private BoardGame lastRemoved;

    public RemoveGameAction(GameService service, UserInput input, ActionHistory history) {
        this.service = service;
        this.input = input;
        this.history = history;
    }

    @Override
    public void execute() {
        List<BoardGame> games = service.getAllGames();

        if (games.isEmpty()) {
            System.out.println("No games in collection.");
            return;
        }

        System.out.println("\n=== Your Board Game Collection ===");
        for (int i = 0; i < games.size(); i++) {
            System.out.println((i + 1) + ". " + games.get(i).getTitle());
        }

        int choice = input.getIntBetween("Select game number to remove", 1, games.size());
        lastRemoved = games.get(choice - 1);

        service.removeGame(lastRemoved);
        history.push(this);

        System.out.println("Board game \"" + lastRemoved.getTitle() + "\" removed successfully.");
    }

    @Override
    public void undo() {
        if (lastRemoved != null) {
            service.addGame(lastRemoved);
            System.out.println("Undone: Added \"" + lastRemoved.getTitle() + "\" back to collection");
        }
    }
}