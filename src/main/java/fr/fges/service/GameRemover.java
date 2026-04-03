package fr.fges.service;

import fr.fges.model.BoardGame;

/** Service pour retirer un jeu : retire du référentiel et sauvegarde. */
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