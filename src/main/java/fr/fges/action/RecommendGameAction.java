package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;

import java.util.Random;

public class RecommendGameAction implements MenuAction {

    private final GameService service;
    private final Random random;

    public RecommendGameAction(GameService service, Random random) {
        this.service = service;
        this.random = random;
    }

    @Override
    public void execute() {
        BoardGame game = service.getRandomGame(random);
        if (game == null) {
            System.out.println("No games available.");
        } else {
            System.out.println("Recommended game: " + game);
        }
    }
}