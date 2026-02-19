package fr.fges.businesslogic;

import fr.fges.model.BoardGame;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


public class RecommendationService {

    private final Random random = new Random();


    public Optional<BoardGame> recommend(List<BoardGame> games, int playerCount) {
        List<BoardGame> compatible = games.stream()
                .filter(g -> playerCount >= g.getMinPlayers() && playerCount <= g.getMaxPlayers())
                .collect(Collectors.toList());

        if (compatible.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(compatible.get(random.nextInt(compatible.size())));
    }
}