package fr.fges;

public class MenuActions {

    private final UserInput input;

    public MenuActions(UserInput input) {
        this.input = input;
    }

    public void addGame() {
        BoardGame game = readGameFromUser();
        GameCollection.addGame(game);
        System.out.println("Board game added successfully.");
    }

    public void removeGame() {
        String title = input.getString("Title of game to remove");

        for (BoardGame game : GameCollection.getGames()) {
            if (game.title().equals(title)) {
                GameCollection.removeGame(game);
                System.out.println("Board game removed successfully.");
                return;
            }
        }

        System.out.println("No board game found with that title.");
    }

    public void listAllGames() {
        GameCollection.viewAllGames();
    }

    public void exit() {
        System.out.println("Exiting the application. Goodbye!");
        System.exit(0);
    }

    private BoardGame readGameFromUser() {
        String title = input.getString("Title");
        int minPlayers = input.getIntAtLeast("Minimum Players", 1);
        int maxPlayers = input.getIntAtLeastOther("Maximum Players", minPlayers);
        String category = input.getString("Category (e.g., fantasy, cooperative, family, strategy)");

        return new BoardGame(title, minPlayers, maxPlayers, category);
    }
}
