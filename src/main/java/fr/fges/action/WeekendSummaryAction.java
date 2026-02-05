package fr.fges.action;

import fr.fges.model.BoardGame;
import fr.fges.policy.DayPolicy;
import fr.fges.service.GameService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeekendSummaryAction implements MenuAction {

    private final GameService service;
    private final DayPolicy policy;

    public WeekendSummaryAction(GameService service, DayPolicy policy) {
        this.service = service;
        this.policy = policy;
    }

    @Override
    public void execute() {
        List<BoardGame> allGames = service.getAllGames();

        if (allGames.isEmpty()) {
            System.out.println("No games in collection.");
            return;
        }

        System.out.println("\n=== Summary (3 random games) ===");

        // Si moins de 3 jeux, afficher tous
        if (allGames.size() <= 3) {
            for (BoardGame game : allGames) {
                System.out.println("- " + game.getTitle() + " (" +
                        game.getMinPlayers() + "-" + game.getMaxPlayers() +
                        " players, " + game.getCategory() + ")");
            }
        } else {
            // Sinon, afficher 3 jeux aléatoires
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