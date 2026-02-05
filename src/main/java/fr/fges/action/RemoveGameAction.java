package fr.fges.action;

import fr.fges.service.GameService;
import fr.fges.ui.UserInput;

public class RemoveGameAction implements MenuAction {

    private final UserInput input;
    private final GameService service;

    public RemoveGameAction(UserInput input, GameService service) {
        this.input = input;
        this.service = service;
    }

    @Override
    public void execute() {
        String title = input.getString("Game title to remove");
        service.removeGame(title);
        System.out.println("Game removed (if it existed).");
    }
}