package fr.fges;

import fr.fges.action.*;
import fr.fges.businesslogic.ActionHistory;
import fr.fges.businesslogic.UndoLastAction;
import fr.fges.policy.DayPolicy;
import fr.fges.policy.SystemDayPolicy;
import fr.fges.policy.WeekendSummaryAction;
import fr.fges.service.GameAdder;
import fr.fges.service.GameFinder;
import fr.fges.service.GameLoader;
import fr.fges.service.GameRemover;
import fr.fges.service.GameRepository;
import fr.fges.service.GameSaver;
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

        GameRepository repository = new GameRepository();
        GameLoader loader = new GameLoader(storage);
        GameSaver saver = new GameSaver(storage);
        GameAdder adder = new GameAdder(repository, saver);
        GameRemover remover = new GameRemover(repository, saver);
        GameFinder finder = new GameFinder(repository);

        repository.setGames(loader.load());

        UserInput input = new UserInput();
        GamePrinter printer = new GamePrinter();

        DayPolicy policy = new SystemDayPolicy();
        ActionHistory history = new ActionHistory();

        List<MenuAction> actions = new ArrayList<>();
        actions.add(new AddGameAction(adder, remover, input, history));
        actions.add(new RemoveGameAction(repository, remover, adder, input, history));
        actions.add(new ListGamesAction(repository, printer));
        actions.add(new RecommendGameAction(repository, input));
        actions.add(new FindGamesByPlayersAction(finder, input));
        actions.add(new UndoLastAction(history));
        actions.add(new TournamentAction(repository, input));

        if (policy.isWeekend()) {
            actions.add(new WeekendSummaryAction(repository));
        }

        actions.add(new ExitAction());

        Menu menu = new Menu(input, actions, policy);
        menu.run();
    }
}