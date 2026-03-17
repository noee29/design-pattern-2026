package fr.fges.service;

import fr.fges.model.BoardGame;

import java.util.List;

public class GameFinder {

    private final GameRepository repository;

    public GameFinder(GameRepository repository) {
        this.repository = repository;
    }

    public List<BoardGame> findGamesByPlayers(int playerCount) {
        return repository.getGames().stream()
                .filter(g -> playerCount >= g.getMinPlayers() && playerCount <= g.getMaxPlayers())
                .sorted((a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()))
                .toList();
    }

    public boolean gameExists(String title) {
        return repository.getGames().stream()
                .anyMatch(game -> game.getTitle().equalsIgnoreCase(title));
    }
}