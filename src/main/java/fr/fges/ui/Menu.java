package fr.fges.ui;

import fr.fges.action.MenuAction;
import fr.fges.policy.DayPolicy;

import java.util.Map;

public class Menu {

    private final UserInput input;
    private final Map<Integer, MenuAction> actions;
    private final DayPolicy dayPolicy;

    public Menu(UserInput input, Map<Integer, MenuAction> actions, DayPolicy dayPolicy) {
        this.input = input;
        this.actions = actions;
        this.dayPolicy = dayPolicy;
    }

    public void run() {
        while (true) {
            show();
            String choice = input.readChoice();

            int max = dayPolicy.isWeekend() ? 6 : 5;

            int c;
            try {
                c = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice.");
                continue;
            }

            if (c < 1 || c > max) {
                System.out.println("Invalid choice.");
                continue;
            }

            if (!dayPolicy.isWeekend() && c == 5) {
                actions.get(6).execute(); // exit
                continue;
            }

            actions.get(c).execute();
        }
    }

    private void show() {
        System.out.println("""
        1. Add Board Game
        2. Remove Board Game
        3. List All Board Games
        4. Recommend Game
        """);

        if (dayPolicy.isWeekend()) {
            System.out.println("""
            5. View Summary (Weekend Special!)
            6. Exit
            """);
        } else {
            System.out.println("""
            5. Exit
            """);
        }
        System.out.print("Please select an option: ");
    }
}
