package fr.fges;

import fr.fges.action.*;
import fr.fges.history.ActionHistory;
import fr.fges.policy.DayPolicy;
import fr.fges.policy.SystemDayPolicy;
import fr.fges.service.GameService;
import fr.fges.storage.JsonStorage;
import fr.fges.storage.StorageStrategy;
import fr.fges.ui.GamePrinter;
import fr.fges.ui.Menu;
import fr.fges.ui.UserInput;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        StorageStrategy storage = new JsonStorage("games.json");
        GameService service = new GameService(storage);

        UserInput input = new UserInput();
        GamePrinter printer = new GamePrinter();

        DayPolicy policy = new SystemDayPolicy();

        ActionHistory history = new ActionHistory();

        Map<Integer, MenuAction> actions = new HashMap<>();
        actions.put(1, new AddGameAction(service, input, history));
        actions.put(2, new RemoveGameAction(service, input, history));
        actions.put(3, new ListGamesAction(service, printer));
        actions.put(4, new RecommendGameAction(service, input));

        if (policy.isWeekend()) {
            actions.put(5, new UndoLastAction(history));
            actions.put(6, new ExitAction());
        } else {
            actions.put(5, new UndoLastAction(history));
            actions.put(6, new ExitAction());
        }

        Menu menu = new Menu(input, actions, policy);
        menu.run();
    }
}