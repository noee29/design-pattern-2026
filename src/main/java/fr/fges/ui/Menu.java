package fr.fges.ui;

import fr.fges.action.MenuAction;
import fr.fges.policy.DayPolicy;

import java.util.List;

public class Menu {

    private final UserInput input;
    private final List<MenuAction> actions;
    private final DayPolicy policy;

    public Menu(UserInput input, List<MenuAction> actions, DayPolicy policy) {
        this.input = input;
        this.actions = actions;
        this.policy = policy;
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
        System.out.println("1. Add Board Game");
        System.out.println("2. Remove Board Game");
        System.out.println("3. List All Board Games");
        System.out.println("4. Recommend Game");
        System.out.println("5. Games for X Players");
        System.out.println("6. Undo Last Action");

        if (policy.isWeekend()) {
            System.out.println("7. Weekend Summary");
            System.out.println("8. Exit");
        } else {
            System.out.println("7. Exit");
        }
    }

    private void executeAction(int choice) {
        int index = choice - 1;
        if (index >= 0 && index < actions.size()) {
            actions.get(index).execute();
        } else {
            System.out.println("Invalid choice.");
        }
    }
}