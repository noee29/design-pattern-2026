package fr.fges.service;

import fr.fges.model.BoardGame;

/** Service pour ajouter un jeu : ajoute au référentiel et sauvegarde. */
public class GameAdder {

    private final GameRepository repository;
    private final GameSaver saver;

    public GameAdder(GameRepository repository, GameSaver saver) {
        this.repository = repository;
        this.saver = saver;
    }

    public void addGame(BoardGame game) {
        repository.add(game);
        saver.save(repository.getGames());
    }
}