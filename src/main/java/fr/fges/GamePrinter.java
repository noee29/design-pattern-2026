package fr.fges;

import java.util.List;

public class GamePrinter {

    public void viewAllGames(GameRepository repository) {
        if (repository.isEmpty()) {
            displayEmptyMessage();
            return;
        }

        displayGames(repository.getGamesSortedByTitle());
    }

    private void displayEmptyMessage() {
        System.out.println("No board games in collection.");
    }

    private void displayGames(List<BoardGame> games) {
        for (BoardGame game : games) {
            System.out.println(formatGame(game));
        }
    }

    private String formatGame(BoardGame game) {
        return "Game: " + game.title() +
                " (" + game.minPlayers() + "-" +
                game.maxPlayers() + " players) - " +
                game.category();
    }
}
