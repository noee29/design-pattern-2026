package fr.fges.samplecode;

import fr.fges.policy.DayPolicy;
import fr.fges.policy.SystemDayPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemDayPolicyTest {

    @Test
    void isWeekend_shouldReturnBooleanWithoutCrash() {
        // Arrange
        DayPolicy policy = new SystemDayPolicy();

        // Act
        boolean result = policy.isWeekend();

        // Assert
        assertNotNull(result);
    }
}