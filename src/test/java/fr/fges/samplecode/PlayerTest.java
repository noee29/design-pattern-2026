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

    @Test
    void constructor_shouldInitializeWithZeroPointsAndWins() {
        // Arrange & Act — player create in setUp()

        // Assert
        assertEquals("Alice", player.getName());
        assertEquals(0, player.getPoints());
        assertEquals(0, player.getWins());
    }

    @Test
    void addWin_shouldAdd3PointsAndIncrementWins() {
        // Arrange — player with 0 points

        // Act
        player.addWin();

        // Assert
        assertEquals(3, player.getPoints());
        assertEquals(1, player.getWins());
    }

    @Test
    void addLoss_shouldAdd1Point() {
        // Arrange — player with 0 points

        // Act
        player.addLoss();

        // Assert
        assertEquals(1, player.getPoints());
        assertEquals(0, player.getWins()); // pas de victoire ajoutée
    }

    @Test
    void addWin_calledTwice_shouldAccumulateCorrectly() {
        // Arrange — player with 0 points

        // Act
        player.addWin();
        player.addWin();

        // Assert
        assertEquals(6, player.getPoints());
        assertEquals(2, player.getWins());
    }

    @Test
    void toString_shouldContainNameAndPoints() {
        // Arrange
        player.addWin();

        // Act
        String result = player.toString();

        // Assert
        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("3"));
    }

    @Test
    void toString_shouldUseSingularWin_whenOneWin() {
        // Arrange
        player.addWin();

        // Act
        String result = player.toString();

        // Assert
        assertTrue(result.contains("1 win)"));
    }

    @Test
    void toString_shouldUsePluralWins_whenMultipleWins() {
        // Arrange
        player.addWin();
        player.addWin();

        // Act
        String result = player.toString();

        // Assert
        assertTrue(result.contains("wins)"));
    }
}