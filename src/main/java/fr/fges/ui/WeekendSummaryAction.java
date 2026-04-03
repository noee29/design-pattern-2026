package fr.fges.ui;

import fr.fges.model.BoardGame;
import fr.fges.service.GameRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Action spéciale lors du weekend : affiche 3 jeux aléatoires de la collection. */
public class WeekendSummaryAction implements MenuEntry {

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

        List<BoardGame> shuffled = new ArrayList<>(allGames);
        Collections.shuffle(shuffled);

        for (int i = 0; i < Math.min(3, shuffled.size()); i++) {
            System.out.println("- " + shuffled.get(i));
        }
    }
}