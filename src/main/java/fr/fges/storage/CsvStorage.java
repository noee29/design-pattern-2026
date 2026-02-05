package fr.fges.storage;

import fr.fges.model.BoardGame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvStorage implements StorageStrategy {

    private final String file;

    public CsvStorage(String file) {
        this.file = file;
    }

    @Override
    public List<BoardGame> load() throws IOException {
        List<BoardGame> games = new ArrayList<>();

        File f = new File(file);
        if (!f.exists()) {
            return games;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                games.add(new BoardGame(
                        parts[0],
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        parts[3]
                ));
            }
        }

        return games;
    }

    @Override
    public void save(List<BoardGame> games) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("title,minPlayers,maxPlayers,category");
            writer.newLine();

            for (BoardGame g : games) {
                writer.write(
                        g.getTitle() + "," +
                                g.getMinPlayers() + "," +
                                g.getMaxPlayers() + "," +
                                g.getCategory()
                );
                writer.newLine();
            }
        }
    }
}