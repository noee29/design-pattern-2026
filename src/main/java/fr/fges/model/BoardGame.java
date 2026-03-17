package fr.fges.model;

public class BoardGame {

    private String title;
    private int minPlayers;
    private int maxPlayers;
    private String category;

    private static final String FORMAT = "%s (%d-%d players, %s)";

    public BoardGame() {
    }

    public BoardGame(String title, int minPlayers, int maxPlayers, String category) {
        this.title = title;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.category = category;
    }

    public String getTitle() { return title; }
    public int getMinPlayers() { return minPlayers; }
    public int getMaxPlayers() { return maxPlayers; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return String.format(FORMAT, title, minPlayers, maxPlayers, category);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BoardGame other)) return false;
        return title.equalsIgnoreCase(other.title);
    }

    @Override
    public int hashCode() {
        return title.toLowerCase().hashCode();
    }
}