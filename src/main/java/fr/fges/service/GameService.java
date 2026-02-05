package fr.fges.service;

import fr.fges.model.BoardGame;
import fr.fges.storage.StorageStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameService {

    private final StorageStrategy storage;
    private final List<BoardGame> games = new ArrayList<>();

    public GameService(StorageStrategy storage) {
        this.storage = storage;
        loadFromStorage();
    }

    private void loadFromStorage() {
        try {
            games.addAll(storage.load());
        } catch (IOException e) {
            System.err.println("Error loading games: " + e.getMessage());
        }
    }

    public void addGame(BoardGame game) {
        games.add(game);
        saveToStorage();
    }

    public void removeGame(BoardGame game) {
        games.remove(game);
        saveToStorage();
    }

    public List<BoardGame> getAllGames() {
        return new ArrayList<>(games);
    }

    private void saveToStorage() {
        try {
            storage.save(games);
        } catch (IOException e) {
            System.err.println("Error saving games: " + e.getMessage());
        }
    }
}