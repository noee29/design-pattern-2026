package fr.fges.service;

import fr.fges.model.BoardGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameService {

    private final List<BoardGame> games = new ArrayList<>();

    public void addGame(BoardGame game) {
        games.add(game);
    }

    public void removeGame(String title) {
        games.removeIf(g -> g.getTitle().equalsIgnoreCase(title));
    }

    public List<BoardGame> getAllGames() {
        return games;
    }

    public BoardGame getRandomGame(Random random) {
        if (games.isEmpty()) return null;
        return games.get(random.nextInt(games.size()));
    }
}