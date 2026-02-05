package fr.fges.ui;

import fr.fges.model.BoardGame;
import java.util.List;

public class GamePrinter {

    public void viewAllGames(List<BoardGame> games) {
        if (games.isEmpty()) {
            System.out.println("No board games in collection.");
            return;
        }

        for (BoardGame g : games) {
            System.out.println(
                    "Game: " + g.title() +
                            " (" + g.minPlayers() + "-" +
                            g.maxPlayers() + " players) - " +
                            g.category()
            );
        }
    }
}