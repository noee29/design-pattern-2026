package fr.fges.service;

import fr.fges.model.BoardGame;
import fr.fges.storage.StorageStrategy;

import java.io.IOException;
import java.util.List;

/** Service de sauvegarde des jeux : délègue au StorageStrategy et gère les erreurs. */
public class GameSaver {

    private final StorageStrategy storage;

    public GameSaver(StorageStrategy storage) {
        this.storage = storage;
    }

    public void save(List<BoardGame> games) {
        try {
            storage.save(games);
        } catch (IOException e) {
            System.err.println("Error saving games: " + e.getMessage());
        }
    }
}