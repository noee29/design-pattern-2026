package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;

public class AddGameAction implements MenuAction {

    private final UserInput input;
    private final GameService service;

    public AddGameAction(UserInput input, GameService service) {
        this.input = input;
        this.service = service;
    }

    @Override
    public void execute() {
        String title = input.getString("Title");
        int min = input.getIntAtLeast("Min players", 1);
        int max = input.getIntAtLeast("Max players", min);
        String category = input.getString("Category");

        service.addGame(new BoardGame(title, min, max, category));
        System.out.println("Game added.");
    }
}