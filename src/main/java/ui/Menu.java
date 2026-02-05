package ui;

import fr.fges.action.MenuAction;
import fr.fges.policy.DayPolicy;

import java.util.List;
import java.util.Random;

public class Menu {

    private final fr.fges.ui.UserInput input;
    private final MenuActions actions;

    public Menu(fr.fges.service.GameService service, DayPolicy dayPolicy) {

        this.input = new fr.fges.ui.UserInput();
        GamePrinter printer = new GamePrinter();
        Random random = new Random();

        this.actions = new MenuActions(
                input,
                service,
                printer,
                random,
                dayPolicy
        );
    }

    public void handleMenu() {
        while (true) {
            displayMainMenu();
            String choice = input.readChoice();
            handleChoice(choice);
        }
    }

    private void displayMenu() {
        System.out.println("""
            === Board Game Collection ===
            1. Add Board Game
            2. Remove Board Game
            3. List All Board Games
            4. Recommend Game
            5. Weekend Summary
            6. Exit
            Choose an option:
            """);
    }
}