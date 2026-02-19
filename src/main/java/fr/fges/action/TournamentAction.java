package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.tournament.*;
import fr.fges.ui.UserInput;

import java.util.ArrayList;
import java.util.List;

public class TournamentAction implements MenuAction {

    private static final int MIN_PLAYERS = 3;
    private static final int MAX_PLAYERS = 8;

    private final GameService service;
    private final UserInput input;

    public TournamentAction(GameService service, UserInput input) {
        this.service = service;
        this.input = input;
    }

    @Override
    public String getLabel() { return "Tournament Mode"; }

    @Override
    public void execute() {
        System.out.println("\n=== Tournament Mode ===");

        BoardGame game = selectGame();
        if (game == null) return;

        List<Player> players = collectPlayers();
        if (players == null) return;

        TournamentStrategy strategy = selectStrategy();

        Tournament tournament = new Tournament(players, strategy, input);
        tournament.play();
    }

    private BoardGame selectGame() {
        List<BoardGame> twoPlayerGames = getTwoPlayerGames();

        if (twoPlayerGames.isEmpty()) {
            System.out.println("No 2-player games available.");
            return null;
        }

        System.out.println("Available 2-player games:");
        for (int i = 0; i < twoPlayerGames.size(); i++) {
            System.out.println((i + 1) + ". " + twoPlayerGames.get(i));
        }

        int choice = input.getIntBetween("Select game", 1, twoPlayerGames.size());
        return twoPlayerGames.get(choice - 1);
    }

    private List<BoardGame> getTwoPlayerGames() {
        List<BoardGame> result = new ArrayList<>();
        for (BoardGame game : service.getAllGames()) {
            if (game.getMinPlayers() <= 2 && game.getMaxPlayers() >= 2) {
                result.add(game);
            }
        }
        return result;
    }

    private List<Player> collectPlayers() {
        int count = input.getIntBetween("Number of participants", MIN_PLAYERS, MAX_PLAYERS);

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String name = input.getString("Enter player " + i + " name: ");
            players.add(new Player(name));
        }
        return players;
    }

    private TournamentStrategy selectStrategy() {
        System.out.println("\nChoose format:");
        System.out.println("1. Championship (everyone plays everyone)");
        System.out.println("2. King of the Hill (winner stays)");

        int choice = input.getIntBetween("Select format", 1, 2);
        return (choice == 1) ? new ChampionshipStrategy() : new KingOfHillStrategy();
    }
}