package fr.fges.ui;

import fr.fges.action.MenuAction;
import fr.fges.policy.DayPolicy;

import java.util.Map;

public class Menu {

    private final UserInput input;
    private final Map<Integer, MenuAction> actions;
    private final DayPolicy policy;

    public Menu(UserInput input, Map<Integer, MenuAction> actions, DayPolicy policy) {
        this.input = input;
        this.actions = actions;
        this.policy = policy;
    }

    public void run() {
        while (true) {
            displayMenu();

            int maxChoice = policy.isWeekend() ? 7 : 7;

            int choice = input.getIntBetween("Please select an option", 1, maxChoice);
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

        if (policy.isWeekend()) {
            System.out.println("6. Undo Last Action");
            System.out.println("7. Exit");
        } else {
            System.out.println("6. Undo Last Action");
            System.out.println("7. Exit");
        }
    }

    private void executeAction(int choice) {
        MenuAction action = actions.get(choice);
        if (action != null) {
            action.execute();
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
