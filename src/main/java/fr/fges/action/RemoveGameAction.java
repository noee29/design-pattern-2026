package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;

import java.util.List;

public class RemoveGameAction implements MenuAction {

    private final GameService service;
    private final UserInput input;

    public RemoveGameAction(GameService service, UserInput input) {
        this.service = service;
        this.input = input;
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

        BoardGame gameToRemove = games.get(choice - 1);
        service.removeGame(gameToRemove);
        System.out.println("Board game \"" + gameToRemove.getTitle() + "\" removed successfully.");
    }
}