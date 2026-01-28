package fr.fges;

public class Menu {

    private final UserInput input;
    private final MenuActions actions;

    public Menu() {
        this.input = new UserInput();
        this.actions = new MenuActions(input);
    }

    public void handleMenu() {
        while (true) {
            displayMainMenu();
            String choice = input.readChoice();
            handleChoice(choice);
        }
    }

    public void displayMainMenu() {
        System.out.println("""
                === Board Game Collection ===
                1. Add Board Game
                2. Remove Board Game
                3. List All Board Games
                4. Exit
                Please select an option (1-4):
                """);
    }

    private void handleChoice(String choice) {
        while (true) {
            switch (choice) {
                case "1" -> { actions.addGame(); return; }
                case "2" -> { actions.removeGame(); return; }
                case "3" -> { actions.listAllGames(); return; }
                case "4" -> actions.exit();
                default -> {
                    System.out.println("Invalid choice. Please select a valid option (1-4):");
                    choice = input.readChoice();
                }
            }
        }
    }
}
