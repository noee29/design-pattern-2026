package fr.fges.businesslogic;

import fr.fges.ui.MenuEntry;

public class UndoLastAction implements MenuEntry {

    private final ActionHistory history;

    public UndoLastAction(ActionHistory history) {
        this.history = history;
    }

    @Override
    public String getLabel() {
        return "Undo Last Action";
    }

    @Override
    public void execute() {
        if (history.isEmpty()) {
            System.out.println("No action to undo.");
            return;
        }

        history.pop().undo();
    }
}