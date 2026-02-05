package fr.fges.action;

import fr.fges.service.GameService;
import fr.fges.ui.GamePrinter;

public class ListGamesAction implements MenuAction {

    private final GameService service;
    private final GamePrinter printer;

    public ListGamesAction(GameService service, GamePrinter printer) {
        this.service = service;
        this.printer = printer;
    }

    @Override
    public void execute() {
        printer.printGames(service.getAllGames());
    }
}