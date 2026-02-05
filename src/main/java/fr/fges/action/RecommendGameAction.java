package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RecommendGameAction implements MenuAction {

    private final GameService service;
    private final UserInput input;
    private final Random random = new Random();

    public RecommendGameAction(GameService service, UserInput input) {
        this.service = service;
        this.input = input;
    }

    @Override
    public void execute() {
        List<BoardGame> allGames = service.getAllGames();

        if (allGames.isEmpty()) {
            System.out.println("No games in collection.");
            return;
        }

        int playerCount = input.getIntAtLeast("How many players?", 1);

        List<BoardGame> compatibleGames = allGames.stream()
                .filter(game -> playerCount >= game.getMinPlayers() &&
                        playerCount <= game.getMaxPlayers())
                .collect(Collectors.toList());

        if (compatibleGames.isEmpty()) {
            System.out.println("No games available for " + playerCount + " players.");
            return;
        }

        BoardGame recommendedGame = compatibleGames.get(random.nextInt(compatibleGames.size()));

        System.out.println("Recommended game: \"" + recommendedGame.getTitle() +
                "\" (" + recommendedGame.getMinPlayers() + "-" +
                recommendedGame.getMaxPlayers() + " players, " +
                recommendedGame.getCategory() + ")");
    }
}