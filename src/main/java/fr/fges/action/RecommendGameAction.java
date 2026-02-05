package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;

import java.util.List;
import java.util.Random;

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
        List<BoardGame> games = service.getAllGames();
        if (games.isEmpty()) {
            System.out.println("No games available.");
            return;
        }
        BoardGame game = games.get(random.nextInt(games.size()));
        System.out.println("Recommended game: " + game.getTitle());
    }
}