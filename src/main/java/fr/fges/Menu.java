package fr.fges;

import java.util.Scanner;

public class Menu {
    // common scanner
    private static final Scanner scanner = new Scanner(System.in);

    public static String getUserInput(String prompt) {
        System.out.printf("%s: ", prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            String input = getUserInput(prompt);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static int getIntAtLeast(String prompt, int minValue) {
        while (true) {
            int value = getIntInput(prompt);
            if (value < minValue) {
                System.out.println("Value must be at least " + minValue + ".");
            }
            else{
                System.out.println("Value must be greater than or equal to " + minValue + ".");
            }

            return value;
        }
    }

    private static int getIntAtLeastOther(String prompt, int minAllowed) {
        while (true) {
            int value = getIntAtLeast(prompt, 0);
            if (value < minAllowed) {
                System.out.println("Value must be greater than or equal to " + minAllowed + ".");
                continue;
            }
            return value;
        }
    }

    public static void displayMainMenu() {
        System.out.println("""
                === Board Game Collection ===
                1. Add Board Game
                2. Remove Board Game
                3. List All Board Games
                4. Exit
                Please select an option (1-4):
                """);
    }

    public static void addGame() {
        BoardGame game = readGameFromUser();
        GameCollection.addGame(game);
        System.out.println("Board game added successfully.");
    }

    public static void removeGame() {
        String title = getUserInput("Title of game to remove");

        for (BoardGame game : GameCollection.getGames()) {
            if (game.title().equals(title)) {
                GameCollection.removeGame(game);
                System.out.println("Board game removed successfully.");
                return;
            }
        }

        System.out.println("No board game found with that title.");
    }

    public static void listAllGames() {
        GameCollection.viewAllGames();
    }

    public static void exit() {
        System.out.println("Exiting the application. Goodbye!");
        System.exit(0);
    }

    // Keeps showing the menu until the user chooses Exit
    public static void handleMenu() {
        while (true) {
            displayMainMenu();
            String choice = scanner.nextLine();
            handleChoiceUntilValid(choice);
        }
    }

    private static void handleChoiceUntilValid(String firstChoice) {
        String choice = firstChoice;

        while (true) {
            switch (choice) {
                case "1" -> { addGame(); return; }
                case "2" -> { removeGame(); return; }
                case "3" -> { listAllGames(); return; }
                case "4" -> exit(); // exits the app
                default -> {
                    System.out.println("Invalid choice. Please select a valid option (1-4):");
                    choice = scanner.nextLine();
                }
            }
        }
    }

    private static BoardGame readGameFromUser() {
        String title = getUserInput("Title");
        int minPlayers = getIntAtLeast("Minimum Players", 0);
        int maxPlayers = getIntAtLeastOther("Maximum Players", minPlayers);
        String category = getUserInput("Category (e.g., fantasy, cooperative, family, strategy)");

        return new BoardGame(title, minPlayers, maxPlayers, category);
    }
}
