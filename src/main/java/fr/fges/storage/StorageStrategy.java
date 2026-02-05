package fr.fges.storage;

import fr.fges.model.BoardGame;

import java.io.IOException;
import java.util.List;

public interface StorageStrategy {
    List<BoardGame> load() throws IOException;
    void save(List<BoardGame> games) throws IOException;
}