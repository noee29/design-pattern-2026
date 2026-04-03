package fr.fges.service;

import fr.fges.model.BoardGame;

import java.util.ArrayList;
import java.util.List;

/** Référentiel en mémoire centralisant l'accès et la manipulation de la collection de jeux. */
public class GameRepository {

    private final List<BoardGame> games = new ArrayList<>();

    public List<BoardGame> getGames() {
        return games;
    }

    public void setGames(List<BoardGame> loadedGames) {
        games.clear();
        games.addAll(loadedGames);
    }

    public void add(BoardGame game) {
        games.add(game);
    }

    public void remove(BoardGame game) {
        games.remove(game);
    }

    public List<BoardGame> findAll() {
        return new ArrayList<>(games);
    }
}