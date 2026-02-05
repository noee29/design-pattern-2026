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

            int maxChoice = policy.isWeekend() ? 6 : 5;

            int choice = input.getIntBetween("Please select an option (1-" + maxChoice + ")", 1, maxChoice);

            executeAction(choice);
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Board Game Collection ===");
        System.out.println("1. Add Board Game");
        System.out.println("2. Remove Board Game");
        System.out.println("3. List All Board Games");
        System.out.println("4. Recommend Game");

        if (policy.isWeekend()) {
            System.out.println("5. View Summary (Weekend Special!)");
            System.out.println("6. Exit");
        } else {
            System.out.println("5. Exit");
        }
    }

    private void executeAction(int choice) {
        int actionKey;

        if (policy.isWeekend()) {
            actionKey = choice;
        } else {
            if (choice == 5) {
                actionKey = 6;
            } else {
                actionKey = choice;
            }
        }

        MenuAction action = actions.get(actionKey);
        if (action != null) {
            action.execute();
        } else {
            System.out.println("Invalid choice.");
        }
    }
}