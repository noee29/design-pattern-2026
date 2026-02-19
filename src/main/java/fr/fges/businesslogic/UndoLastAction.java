package fr.fges.businesslogic;

import fr.fges.action.MenuAction;
import fr.fges.model.BoardGame;


public class UndoLastAction implements MenuAction {

    private final ActionHistory history;

    public UndoLastAction(ActionHistory history) {
        this.history = history;
    }

    @Override
    public void execute() {
        if (history.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        UndoableAction<BoardGame> last = history.pop();
        last.undo();
    }
}