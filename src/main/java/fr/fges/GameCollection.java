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
    // kept one functionality related to view games
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
    // add it for future needs if we wont to
    private static void displayEmptyMessage() {
        System.out.println("No board games in collection.");
     }
     // here we put the 2nd functionality of the old view all games
    private static void displayGames(List<BoardGame> games) {
        for (BoardGame game : games) {
            System.out.println(formatGame(game));
        }
    }
    // did the format alone in case we might use or need to change it that way it's easier and clearer
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

    // Loads board games from the storage file if it exists, automatically detecting JSON or CSV format
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
    // Saves the current list of board games to the storage file using the appropriate format (JSON or CSV)
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

    // Saves the current list of board games to a JSON file using a formatted output
     private static void loadFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<BoardGame> loadedGames = mapper.readValue(
                new File(storageFile),
                new TypeReference<>() {
                }
        );
        replaceGames(loadedGames);
    }

    private static void saveToJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(storageFile), games);
    }
    // Loads board games from a CSV file while skipping the header and validating each line
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
    // Saves the current list of board games to a CSV file including a header row
    private static void saveToCsv() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile))) {
            writeCsvHeader(writer);
            writeCsvGames(writer);
        }
    }
    // Skips the header line of a CSV file before reading data rows
    private static void skipHeader(BufferedReader reader) throws IOException {
        reader.readLine();
    }
    // Parses a single CSV line into a BoardGame object, returning null if the data is invalid
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
    // Writes the header row for the CSV file
    private static void writeCsvHeader(BufferedWriter writer) throws IOException {
        writer.write("title,minPlayers,maxPlayers,category");
        writer.newLine();
    }
    // Writes all board games to the CSV file in comma-separated format
    private static void writeCsvGames(BufferedWriter writer) throws IOException {
        for (BoardGame game : games) {
            writer.write(game.title() + "," +
                    game.minPlayers() + "," +
                    game.maxPlayers() + "," +
                    game.category());
            writer.newLine();
        }
    }

    // Replaces the current board game list with a new list of loaded games
    private static void replaceGames(List<BoardGame> newGames) {
        games.clear();
        games.addAll(newGames);
    }
}
