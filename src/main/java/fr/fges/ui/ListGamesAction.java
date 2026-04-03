package fr.fges.ui;

import fr.fges.service.GameRepository;

/** Action pour afficher tous les jeux de la collection. */
public class ListGamesAction implements MenuEntry {

    private final GameRepository repository;
    private final GamePrinter printer;

    public ListGamesAction(GameRepository repository, GamePrinter printer) {
        this.repository = repository;
        this.printer = printer;
    }

    @Override
    public String getLabel() {
        return "List All Board Games";
    }

    @Override
    public void execute() {
        printer.printGames(repository.findAll());
    }
}