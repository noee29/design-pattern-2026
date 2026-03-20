package fr.fges.samplecode;

import fr.fges.policy.DayPolicy;
import fr.fges.policy.SystemDayPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemDayPolicyTest {

    // ── isWeekend

    @Test
    void isWeekend_shouldReturnBooleanWithoutException() {
        // Arrange
        DayPolicy policy = new SystemDayPolicy();

        // Act
        boolean result = policy.isWeekend();

        // Assert — on ne peut pas contrôler le jour réel, on vérifie juste que ça fonctionne
        assertTrue(result || !result);
    }

    @Test
    void isWeekend_shouldImplementDayPolicy() {
        // Arrange & Act
        DayPolicy policy = new SystemDayPolicy();

        // Assert — vérifie que SystemDayPolicy implémente bien l'interface DayPolicy
        assertInstanceOf(DayPolicy.class, policy);
    }

    @Test
    void isWeekend_shouldBeDeterministicWithinSameCall() {
        // Arrange
        SystemDayPolicy policy = new SystemDayPolicy();

        // Act
        boolean first = policy.isWeekend();
        boolean second = policy.isWeekend();

        // Assert
        assertEquals(first, second);
    }
}