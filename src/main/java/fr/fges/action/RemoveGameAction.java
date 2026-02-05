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
            System.out.println("No games available.");
            return;
        }
        for (int i = 0; i < games.size(); i++) {
            System.out.println((i + 1) + ". " + games.get(i).getTitle());
        }
        int choice = input.getIntAtLeast("Select game to remove", 1);
        if (choice > 0 && choice <= games.size()) {
            service.removeGame(games.get(choice - 1));
            System.out.println("Game removed.");
        }
    }
}