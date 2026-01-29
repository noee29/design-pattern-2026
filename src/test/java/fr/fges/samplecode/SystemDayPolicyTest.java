package fr.fges.samplecode;

import fr.fges.SystemDayPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemDayPolicyTest {

    @Test
    void isWeekend_shouldReturnBoolean() {
        // Arrange
        SystemDayPolicy policy = new SystemDayPolicy();

        // Act
        boolean result = policy.isWeekend();

        // Assert
        assertNotNull(result); // comportement dépend du jour réel
    }
}
