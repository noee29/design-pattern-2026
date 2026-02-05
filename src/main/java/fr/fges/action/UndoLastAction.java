package fr.fges.action;

import fr.fges.history.ActionHistory;

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

        UndoableAction last = history.pop();
        last.undo();
    }
}