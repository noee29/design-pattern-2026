package fr.fges;

import java.util.List;
import java.util.Random;

public class MenuActions {

    private final UserInput input;
    private final GameRepository repository;
    private final GamePrinter printer;
    private final Random random;
    private final DayPolicy dayPolicy;


    public MenuActions(UserInput input, GameRepository repository, GamePrinter printer, Random random, DayPolicy dayPolicy) {
        this.input = input;
        this.repository = repository;
        this.printer = printer;
        this.random = random;
        this.dayPolicy = dayPolicy;
    }
    public void weekendSummary() {

        if (!isSummaryAllowed()) {
            displaySummaryNotAllowed();
            return;
        }

        if (repository.isEmpty()) {
            displayEmptyCollection();
            return;
        }

        displaySummaryHeader();
        displayRandomGamesSummary();
    }

    private boolean isSummaryAllowed() {
        return dayPolicy.isWeekend();
    }

    private void displaySummaryNotAllowed() {
        System.out.println("Summary is only available on weekends.");
    }

    private void displayEmptyCollection() {
        System.out.println("No board games in collection.");
    }
    private void displaySummaryHeader() {
        System.out.println("=== Summary (3 random games) ===");
    }
    private void displayRandomGamesSummary() {

        var games = repository.getGames();
        int count = getSummaryCount(games.size());

        for (int i = 0; i < count; i++) {
            BoardGame game = pickRandomGame(games);
            displayGame(game);
        }
    }
    private int getSummaryCount(int totalGames) {
        return Math.min(3, totalGames);
    }
    private BoardGame pickRandomGame(List<BoardGame> games) {
        return games.get(random.nextInt(games.size()));
    }
    private void displayGame(BoardGame game) {
        System.out.println("- " + game);
    }

    public void addGame() {
        try {
            BoardGame game = readGameFromUser();
            repository.addGame(game);
            System.out.println("Board game added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    public void removeGame() {
        String title = input.getString("Title of game to remove");

        for (BoardGame game : repository.getGames()) {
            if (game.title().equalsIgnoreCase(title)) {
                repository.removeGame(game);
                System.out.println("Board game removed successfully.");
                return;
            }
        }

        System.out.println("No board game found with that title.");
    }

    public void listAllGames() {
        printer.viewAllGames(repository);
    }

    /**
     * Separated for testability
     */
    protected void exitApplication() {
        System.exit(0);
    }

    public void exit() {
        System.out.println("Exiting the application. Goodbye!");
        exitApplication();
    }

    private BoardGame readGameFromUser() {
        String title = input.getString("Title");
        int minPlayers = input.getIntAtLeast("Minimum Players", 1);
        int maxPlayers = input.getIntAtLeastOther("Maximum Players", minPlayers);
        String category = input.getString("Category (e.g., fantasy, cooperative, family, strategy)");

        return new BoardGame(title, minPlayers, maxPlayers, category);
    }

    public void recommendGame() {

        int players = input.getIntAtLeast("How many players?", 1);

        var compatibleGames = repository.findCompatibleGames(players);

        if (compatibleGames.isEmpty()) {
            System.out.println("No compatible games found.");
            return;
        }

        int index = random.nextInt(compatibleGames.size());
        BoardGame game = compatibleGames.get(index);

        System.out.println(
                "Recommended game: \"" +
                        game.title() + "\" (" +
                        game.minPlayers() + "-" +
                        game.maxPlayers() + " players, " +
                        game.category() + ")"
        );
    }

}
