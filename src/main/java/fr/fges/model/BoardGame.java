package fr.fges.model;

public class BoardGame {

    private final String title;
    private final int minPlayers;
    private final int maxPlayers;
    private final String category;

    public BoardGame(String title, int minPlayers, int maxPlayers, String category) {
        this.title = title;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return title + " (" + minPlayers + "-" + maxPlayers + " players, " + category + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BoardGame other = (BoardGame) obj;
        return title.equalsIgnoreCase(other.title);
    }

    @Override
    public int hashCode() {
        return title.toLowerCase().hashCode();
    }
}