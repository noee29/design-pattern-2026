package fr.fges.ui;

import fr.fges.businesslogic.RecommendationService;
import fr.fges.model.BoardGame;
import fr.fges.service.GameRepository;

import java.util.List;
import java.util.Optional;

/** Action pour recommander un jeu aléatoire basé sur le nombre de joueurs. */
public class RecommendGameAction implements MenuEntry {

    private final GameRepository repository;
    private final UserInput input;
    private final RecommendationService recommendationService;

    public RecommendGameAction(GameRepository repository, UserInput input) {
        this.repository = repository;
        this.input = input;
        this.recommendationService = new RecommendationService();
    }

    @Override
    public String getLabel() {
        return "Recommend Game";
    }

    @Override
    public void execute() {
        List<BoardGame> allGames = repository.findAll();

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

        System.out.println("Recommended game: " + recommended.get());
    }
}