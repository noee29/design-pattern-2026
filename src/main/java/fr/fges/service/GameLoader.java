package fr.fges.service;

import fr.fges.model.BoardGame;
import fr.fges.storage.StorageStrategy;

import java.io.IOException;
import java.util.List;

public class GameLoader {

    private final StorageStrategy storage;

    public GameLoader(StorageStrategy storage) {
        this.storage = storage;
    }

    public List<BoardGame> load() {
        try {
            return storage.load();
        } catch (IOException e) {
            System.err.println("Error loading games: " + e.getMessage());
            return List.of();
        }
    }
}