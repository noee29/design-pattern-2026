package fr.fges;

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
        if (!f.exists()) return games;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 4) {
                    games.add(new BoardGame(
                            p[0],
                            Integer.parseInt(p[1]),
                            Integer.parseInt(p[2]),
                            p[3]
                    ));
                }
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
                writer.write(g.title() + "," +
                        g.minPlayers() + "," +
                        g.maxPlayers() + "," +
                        g.category());
                writer.newLine();
            }
        }
    }
}
