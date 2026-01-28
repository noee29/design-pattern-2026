package fr.fges.samplecode;

import fr.fges.Menu;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Menu class.
 * Simple tests using AAA pattern.
 */
class MenuTest {

    @Test
    void displayMainMenu_shouldNotCrash() {
        // Arrange
        Menu menu = new Menu();

        // Act & Assert
        assertDoesNotThrow(() -> menu.displayMainMenu());
    }
}
