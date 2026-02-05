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
            int choice = input.getIntBetween("Your choice", 1, maxChoice);
            executeAction(choice);
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Board Game Manager ===");
        System.out.println("1. Add a game");
        System.out.println("2. Remove a game");
        System.out.println("3. List all games");
        System.out.println("4. Get a recommendation");

        if (policy.isWeekend()) {
            System.out.println("5. Weekend summary");
            System.out.println("6. Exit");
        } else {
            System.out.println("5. Exit");
        }
    }

    private void executeAction(int choice) {
        // Ajuster le choix si on n'est pas le weekend
        int actionKey = choice;
        if (!policy.isWeekend() && choice == 5) {
            actionKey = 6; // Exit
        }

        MenuAction action = actions.get(actionKey);
        if (action != null) {
            action.execute();
        } else {
            System.out.println("Invalid choice.");
        }
    }
}