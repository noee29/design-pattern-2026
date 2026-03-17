package fr.fges.action;

import fr.fges.businesslogic.ActionHistory;
import fr.fges.businesslogic.UndoableAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameAdder;
import fr.fges.service.GameRemover;
import fr.fges.service.GameRepository;
import fr.fges.ui.UserInput;

import java.util.List;

public class RemoveGameAction implements UndoableAction<BoardGame> {

    private final GameRepository repository;
    private final GameRemover remover;
    private final GameAdder adder;
    private final UserInput input;
    private final ActionHistory history;
    private BoardGame lastRemoved;

    public RemoveGameAction(GameRepository repository, GameRemover remover, GameAdder adder,
                            UserInput input, ActionHistory history) {
        this.repository = repository;
        this.remover = remover;
        this.adder = adder;
        this.input = input;
        this.history = history;
    }

    @Override
    public String getLabel() {
        return "Remove Board Game";
    }

    @Override
    public void execute() {
        List<BoardGame> games = repository.findAll();

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

        remover.removeGame(lastRemoved);
        history.push(this);

        System.out.println("Board game \"" + lastRemoved.getTitle() + "\" removed successfully.");
    }

    @Override
    public void undo() {
        if (lastRemoved != null) {
            adder.addGame(lastRemoved);
            System.out.println("Undone: Added \"" + lastRemoved.getTitle() + "\" back to collection");
        }
    }
}