package fr.fges.service;

import fr.fges.model.BoardGame;

public class GameRemover {

    private final GameRepository repository;
    private final GameSaver saver;

    public GameRemover(GameRepository repository, GameSaver saver) {
        this.repository = repository;
        this.saver = saver;
    }

    public void removeGame(BoardGame game) {
        repository.remove(game);
        saver.save(repository.getGames());
    }
}