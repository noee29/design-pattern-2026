package fr.fges.action;

import fr.fges.businesslogic.RecommendationService;
import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;

import java.util.List;
import java.util.Optional;

public class RecommendGameAction implements MenuAction {

    private final GameService service;
    private final UserInput input;
    private final RecommendationService recommendationService;

    public RecommendGameAction(GameService service, UserInput input) {
        this.service = service;
        this.input = input;
        this.recommendationService = new RecommendationService();
    }

    @Override
    public void execute() {
        List<BoardGame> allGames = service.getAllGames();

        if (allGames.isEmpty()) {
            System.out.println("No games in collection.");
            return;
        }

        int playerCount = input.getIntAtLeast("How many players?", 1);

        Optional<BoardGame> recommended = recommendationService.recommend(allGames, playerCount);

        if (recommended.isEmpty()) {
            System.out.println("No games available for " + playerCount + " players.");
            return;
        }

        BoardGame game = recommended.get();
        System.out.println("Recommended game: \"" + game.getTitle() +
                "\" (" + game.getMinPlayers() + "-" +
                game.getMaxPlayers() + " players, " +
                game.getCategory() + ")");
    }
}