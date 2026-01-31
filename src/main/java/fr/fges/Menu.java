package fr.fges;

import java.util.Random;

public class Menu {

    private final UserInput input;
    private final MenuActions actions;

    public Menu(GameRepository repository) {

        this.input = new UserInput();
        GamePrinter printer = new GamePrinter();

        Random random = new Random();
        DayPolicy dayPolicy = new SystemDayPolicy();

        this.actions = new MenuActions(
                input,
                repository,
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

    private void displayMainMenu() {
        System.out.println("""
            === Board Game Collection ===
            1. Add Board Game
            2. Remove Board Game
            3. List All Board Games
            4. Recommend Game
            """);

        if (actions.isWeekend()) {
            System.out.println("""
            5. View Summary (Weekend Special!)
            6. Exit
            Please select an option (1-6):
            """);
        } else {
            System.out.println("""
            5. Exit
            Please select an option (1-5):
            """);
        }
    }
    private void handleChoice(String choice) {
        while (true) {

            if (actions.isWeekend()) {
                switch (choice) {
                    case "1" -> { actions.addGame(); return; }
                    case "2" -> { actions.removeGame(); return; }
                    case "3" -> { actions.listAllGames(); return; }
                    case "4" -> { actions.recommendGame(); return; }
                    case "5" -> { actions.weekendSummary(); return; }
                    case "6" -> actions.exit();
                    default -> {
                        System.out.println("Invalid choice. Please select a valid option.");
                        choice = input.readChoice();
                    }
                }
            } else {
                switch (choice) {
                    case "1" -> { actions.addGame(); return; }
                    case "2" -> { actions.removeGame(); return; }
                    case "3" -> { actions.listAllGames(); return; }
                    case "4" -> { actions.recommendGame(); return; }
                    case "5" -> actions.exit();
                    default -> {
                        System.out.println("Invalid choice. Please select a valid option.");
                        choice = input.readChoice();
                    }
                }
            }
        }
    }
}
