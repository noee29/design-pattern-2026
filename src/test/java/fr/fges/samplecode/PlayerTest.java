package fr.fges.samplecode;

import fr.fges.tournament.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Alice");
    }

    // ── Constructeur

    @Test
    void constructor_shouldSetName() {
        // Arrange & Act — player créé dans setUp()

        // Assert
        assertEquals("Alice", player.getName());
    }

    @Test
    void constructor_shouldInitializePointsAtZero() {
        // Arrange & Act — player créé dans setUp()

        // Assert
        assertEquals(0, player.getPoints());
    }

    @Test
    void constructor_shouldInitializeWinsAtZero() {
        // Arrange & Act — player créé dans setUp()

        // Assert
        assertEquals(0, player.getWins());
    }

    // ── addWin

    @Test
    void addWin_shouldAdd3Points() {
        // Arrange — player à 0 points

        // Act
        player.addWin();

        // Assert
        assertEquals(3, player.getPoints());
    }

    @Test
    void addWin_shouldIncrementWinsByOne() {
        // Arrange — player à 0 victoires

        // Act
        player.addWin();

        // Assert
        assertEquals(1, player.getWins());
    }

    @Test
    void addWin_calledTwice_shouldGive6PointsAnd2Wins() {
        // Arrange — player à 0 points

        // Act
        player.addWin();
        player.addWin();

        // Assert
        assertEquals(6, player.getPoints());
        assertEquals(2, player.getWins());
    }

    // ── addLoss

    @Test
    void addLoss_shouldAdd1Point() {
        // Arrange — player à 0 points

        // Act
        player.addLoss();

        // Assert
        assertEquals(1, player.getPoints());
    }

    @Test
    void addLoss_shouldNotIncrementWins() {
        // Arrange — player à 0 victoires

        // Act
        player.addLoss();

        // Assert
        assertEquals(0, player.getWins());
    }

    // ── toString

    @Test
    void toString_shouldContainName() {
        // Arrange — player "Alice"

        // Act
        String result = player.toString();

        // Assert
        assertTrue(result.contains("Alice"));
    }

    @Test
    void toString_shouldContainPoints_afterWin() {
        // Arrange
        player.addWin();

        // Act
        String result = player.toString();

        // Assert
        assertTrue(result.contains("3"));
    }

    @Test
    void toString_shouldUseSingular_whenOneWin() {
        // Arrange
        player.addWin();

        // Act
        String result = player.toString();

        // Assert
        assertTrue(result.contains("1 win)"));
    }

    @Test
    void toString_shouldUsePlural_whenMultipleWins() {
        // Arrange
        player.addWin();
        player.addWin();

        // Act
        String result = player.toString();

        // Assert
        assertTrue(result.contains("wins)"));
    }
}