package fr.fges;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameCollection {

    private static final List<BoardGame> games = new ArrayList<>();
    private static String storageFile = "";



    public static void setStorageFile(String file) {
        storageFile = file;
    }


    public static List<BoardGame> getGames() {
        return games;
    }

    public static void addGame(BoardGame game) {
        games.add(game);
        saveToFile();
    }

    public static void removeGame(BoardGame game) {
        games.remove(game);
        saveToFile();
    }

    /* ===============================
       VIEW / PRESENTATION
       =============================== */

    public static void viewAllGames() {
        if (isEmpty()) {
            displayEmptyMessage();
            return;
        }

        List<BoardGame> sortedGames = getGamesSortedByTitle();
        displayGames(sortedGames);
    }

    private static boolean isEmpty() {
        return games.isEmpty();
    }

    private static void displayEmptyMessage() {
        System.out.println("No board games in collection.");
    }

    private static void displayGames(List<BoardGame> games) {
        for (BoardGame game : games) {
            System.out.println(formatGame(game));
        }
    }

    private static String formatGame(BoardGame game) {
        return "Game: " + game.title() +
                " (" + game.minPlayers() + "-" +
                game.maxPlayers() + " players) - " +
                game.category();
    }

    private static List<BoardGame> getGamesSortedByTitle() {
        return games.stream()
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }


    public static void loadFromFile() {
        if (!storageFileExists()) {
            return;
        }

        try {
            if (isJsonFile()) {
                loadFromJson();
            } else if (isCsvFile()) {
                loadFromCsv();
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private static void saveToFile() {
        try {
            if (isJsonFile()) {
                saveToJson();
            } else if (isCsvFile()) {
                saveToCsv();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static boolean storageFileExists() {
        return new File(storageFile).exists();
    }

    private static boolean isJsonFile() {
        return storageFile.endsWith(".json");
    }

    private static boolean isCsvFile() {
        return storageFile.endsWith(".csv");
    }


    private static void loadFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<BoardGame> loadedGames = mapper.readValue(
                new File(storageFile),
                new TypeReference<List<BoardGame>>() {}
        );
        replaceGames(loadedGames);
    }

    private static void saveToJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(storageFile), games);
    }

    private static void loadFromCsv() throws IOException {
        List<BoardGame> loadedGames = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(storageFile))) {
            skipHeader(reader);

            String line;
            while ((line = reader.readLine()) != null) {
                BoardGame game = parseCsvLine(line);
                if (game != null) {
                    loadedGames.add(game);
                }
            }
        }

        replaceGames(loadedGames);
    }

    private static void saveToCsv() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile))) {
            writeCsvHeader(writer);
            writeCsvGames(writer);
        }
    }

    private static void skipHeader(BufferedReader reader) throws IOException {
        reader.readLine();
    }

    private static BoardGame parseCsvLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 4) return null;

        try {
            return new BoardGame(
                    parts[0],
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    parts[3]
            );
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void writeCsvHeader(BufferedWriter writer) throws IOException {
        writer.write("title,minPlayers,maxPlayers,category");
        writer.newLine();
    }

    private static void writeCsvGames(BufferedWriter writer) throws IOException {
        for (BoardGame game : games) {
            writer.write(game.title() + "," +
                    game.minPlayers() + "," +
                    game.maxPlayers() + "," +
                    game.category());
            writer.newLine();
        }
    }


    private static void replaceGames(List<BoardGame> newGames) {
        games.clear();
        games.addAll(newGames);
    }
}
