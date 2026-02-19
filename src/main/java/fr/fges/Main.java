package fr.fges;

import fr.fges.action.*;
import fr.fges.businesslogic.ActionHistory;
import fr.fges.businesslogic.UndoLastAction;
import fr.fges.policy.DayPolicy;
import fr.fges.policy.SystemDayPolicy;
import fr.fges.policy.WeekendSummaryAction;
import fr.fges.service.GameService;
import fr.fges.storage.JsonStorage;
import fr.fges.storage.StorageStrategy;
import fr.fges.ui.GamePrinter;
import fr.fges.ui.Menu;
import fr.fges.ui.UserInput;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        StorageStrategy storage = new JsonStorage("games.json");
        GameService service = new GameService(storage);

        UserInput input = new UserInput();
        GamePrinter printer = new GamePrinter();

        DayPolicy policy = new SystemDayPolicy();

        ActionHistory history = new ActionHistory();

        List<MenuAction> actions = new ArrayList<>();
        actions.add(new AddGameAction(service, input, history));
        actions.add(new RemoveGameAction(service, input, history));
        actions.add(new ListGamesAction(service, printer));
        actions.add(new RecommendGameAction(service, input));
        actions.add(new FindGamesByPlayersAction(service, input));
        actions.add(new UndoLastAction(history));
        actions.add(new TournamentAction(service, input));


        if (policy.isWeekend()) {
            actions.add(new WeekendSummaryAction(service));
            actions.add(new ExitAction());
        } else {
            actions.add(new ExitAction());
        }

        Menu menu = new Menu(input, actions, policy);
        menu.run();
    }
}