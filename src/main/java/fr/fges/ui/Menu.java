package fr.fges.ui;

import fr.fges.policy.DayPolicy;

import java.util.List;

/** Gestionnaire de la boucle principale du menu : affiche le menu et exécute les actions sélectionnées. */
public class Menu {

    private final UserInput input;
    private final List<MenuEntry> actions;
    private final DayPolicy policy;

    public Menu(UserInput input, List<MenuEntry> actions, DayPolicy policy) {
        this.input = input;
        this.actions = actions;
        this.policy = policy;
    }

    public void run() {
        while (true) {
            show();
            int choice = input.getIntBetween("Choose an option", 1, actions.size());
            actions.get(choice - 1).execute();
        }
    }

    private void show() {
        System.out.println("\n=== Board Game Manager ===");

        if (policy.isWeekend()) {
            System.out.println("Weekend mode activated!");
        }

        for (int i = 0; i < actions.size(); i++) {
            System.out.println((i + 1) + ". " + actions.get(i).getLabel());
        }
    }
}