package fr.fges.action;

import fr.fges.service.GameRepository;
import fr.fges.ui.GamePrinter;

public class ListGamesAction implements MenuAction {

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