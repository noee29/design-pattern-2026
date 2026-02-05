package fr.fges.ui;

import fr.fges.model.BoardGame;

import java.util.List;

public class GamePrinter {

    public void printGames(List<BoardGame> games) {
        if (games.isEmpty()) {
            System.out.println("No games in collection.");
            return;
        }

        System.out.println("\n=== Your Board Game Collection ===");
        for (int i = 0; i < games.size(); i++) {
            System.out.println((i + 1) + ". " + games.get(i));
        }
        System.out.println("Total: " + games.size() + " game(s)");
    }
}