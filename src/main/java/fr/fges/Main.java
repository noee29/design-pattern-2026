package fr.fges;

import fr.fges.policy.DayPolicy;
import fr.fges.policy.SystemDayPolicy;
import fr.fges.service.GameService;
import fr.fges.storage.CsvStorage;
import fr.fges.storage.StorageStrategy;
import fr.fges.ui.Menu;

public class Main {

    public static void main(String[] args) {

        // Choix du stockage (exemple CSV)
        StorageStrategy storage = new CsvStorage("games.csv");
        GameService service = new GameService(storage);

        DayPolicy dayPolicy = new SystemDayPolicy();

        // Le Menu gère TOUT en interne
        Menu menu = new Menu(service, dayPolicy);
        menu.handleMenu();
    }
}