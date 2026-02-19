package fr.fges.ui;

import fr.fges.action.MenuAction;
import fr.fges.policy.DayPolicy;

import java.util.List;

public class Menu {

    private final UserInput input;
    private final List<MenuAction> actions;

    public Menu(UserInput input, List<MenuAction> actions, DayPolicy policy) {
        this.input = input;
        this.actions = actions;
    }

    public void run() {
        while (true) {
            displayMenu();
            int choice = input.getIntBetween("Please select an option", 1, actions.size());
            executeAction(choice);
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Board Game Collection ===");
        for (int i = 0; i < actions.size(); i++) {
            System.out.println((i + 1) + ". " + actions.get(i).getLabel());
        }
    }

    private void executeAction(int choice) {
        actions.get(choice - 1).execute();
    }
}