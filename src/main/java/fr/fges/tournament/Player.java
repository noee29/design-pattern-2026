package fr.fges.tournament;

/** Représentation d'un joueur : stocke nom, points (3 par victoire, 1 par défaite) et compteur de victoires. */
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
