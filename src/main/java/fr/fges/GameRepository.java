package fr.fges;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameRepository {

    private final List<BoardGame> games = new ArrayList<>();
    private final StorageStrategy storage;

    public GameRepository(StorageStrategy storage) {
        this.storage = storage;
        loadFromStorage();
    }

    public List<BoardGame> getGames() {
        return games;
    }

    public void addGame(BoardGame game) {

        boolean exists = games.stream()
                .anyMatch(g -> g.title().equalsIgnoreCase(game.title()));

        if (exists) {
            throw new IllegalArgumentException(
                    "Error: A game with title \"" + game.title() + "\" already exists in the collection."
            );
        }

        games.add(game);
        saveToStorage();
    }
    public void removeGame(BoardGame game) {
        games.remove(game);
        saveToStorage();
    }

    public boolean isEmpty() {
        return games.isEmpty();
    }

    public List<BoardGame> getGamesSortedByTitle() {
        return games.stream()
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }

    private void loadFromStorage() {
        try {
            games.addAll(storage.load());
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private void saveToStorage() {
        try {
            storage.save(games);
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public List<BoardGame> findCompatibleGames(int players) {
        return games.stream()
                .filter(g -> players >= g.minPlayers()
                        && players <= g.maxPlayers())
                .toList();
    }


}
