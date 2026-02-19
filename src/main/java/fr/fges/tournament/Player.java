package fr.fges.tournament;

public class Player {

    private final String name;
    private int points;
    private int wins;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.wins = 0;
    }

    public String getName() { return name; }
    public int getPoints() { return points; }
    public int getWins() { return wins; }

    public void addWin() {
        points += 3;
        wins++;
    }

    public void addLoss() {
        points += 1;
    }

    @Override
    public String toString() {
        return name + " - " + points + " points (" + wins + " win" + (wins != 1 ? "s" : "") + ")";
    }
}
