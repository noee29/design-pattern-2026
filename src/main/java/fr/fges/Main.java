package fr.fges;

import fr.fges.action.*;
import fr.fges.policy.SystemDayPolicy;
import fr.fges.service.GameService;
import fr.fges.storage.CsvStorage;
import fr.fges.storage.StorageStrategy;
import fr.fges.ui.GamePrinter;
import fr.fges.ui.Menu;
import fr.fges.ui.UserInput;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        StorageStrategy storage = new CsvStorage("games.csv");
        GameService service = new GameService(storage);

        UserInput input = new UserInput();
        SystemDayPolicy policy = new SystemDayPolicy();
        GamePrinter printer = new GamePrinter();

        Map<Integer, MenuAction> actions = Map.of(
                1, new AddGameAction(service, input),
                2, new RemoveGameAction(service, input),
                3, new ListGamesAction(service, printer),
                4, new RecommendGameAction(service, input),
                5, new WeekendSummaryAction(service),
                6, new ExitAction()
        );

        Menu menu = new Menu(input, actions, policy);
        menu.run();
    }
}
