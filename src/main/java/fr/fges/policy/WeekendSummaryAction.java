package fr.fges.policy;

import fr.fges.action.MenuAction;
import fr.fges.model.BoardGame;
import fr.fges.service.GameRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeekendSummaryAction implements MenuAction {

    private final GameRepository repository;

    public WeekendSummaryAction(GameRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getLabel() {
        return "View Summary (Weekend Special!)";
    }

    @Override
    public void execute() {
        List<BoardGame> allGames = repository.findAll();

        if (allGames.isEmpty()) {
            System.out.println("No games in collection.");
            return;
        }

        System.out.println("\n=== Summary (3 random games) ===");

        if (allGames.size() <= 3) {
            for (BoardGame game : allGames) {
                System.out.println("- " + game.getTitle() + " (" +
                        game.getMinPlayers() + "-" + game.getMaxPlayers() +
                        " players, " + game.getCategory() + ")");
            }
        } else {
            List<BoardGame> shuffled = new ArrayList<>(allGames);
            Collections.shuffle(shuffled);
            for (int i = 0; i < 3; i++) {
                BoardGame game = shuffled.get(i);
                System.out.println("- " + game.getTitle() + " (" +
                        game.getMinPlayers() + "-" + game.getMaxPlayers() +
                        " players, " + game.getCategory() + ")");
            }
        }
    }
}