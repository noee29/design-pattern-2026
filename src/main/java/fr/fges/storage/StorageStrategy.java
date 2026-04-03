 package fr.fges.storage;

import fr.fges.model.BoardGame;

import java.io.IOException;
import java.util.List;

/** Interface pour les stratégies de stockage : abstrait le mécanisme de chargement/sauvegarde (pattern Strategy). */
public interface StorageStrategy {
    List<BoardGame> load() throws IOException;
    void save(List<BoardGame> games) throws IOException;
}