package fr.fges.action;

import fr.fges.businesslogic.ActionHistory;
import fr.fges.businesslogic.UndoableAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameAdder;
import fr.fges.service.GameRemover;
import fr.fges.ui.UserInput;

public class AddGameAction implements UndoableAction<BoardGame> {

    private final GameAdder adder;
    private final GameRemover remover;
    private final UserInput input;
    private final ActionHistory history;
    private BoardGame lastAdded;

    public AddGameAction(GameAdder adder, GameRemover remover, UserInput input, ActionHistory history) {
        this.adder = adder;
        this.remover = remover;
        this.input = input;
        this.history = history;
    }

    @Override
    public String getLabel() {
        return "Add Board Game";
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
        adder.addGame(lastAdded);
        history.push(this);

        System.out.println("Board game added successfully.");
    }

    @Override
    public void undo() {
        if (lastAdded != null) {
            remover.removeGame(lastAdded);
            System.out.println("Undone: Removed \"" + lastAdded.getTitle() + "\" from collection");
        }
    }
}