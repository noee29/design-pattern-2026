package fr.fges.samplecode;

import fr.fges.businesslogic.ActionHistory;
import fr.fges.businesslogic.UndoableAction;
import fr.fges.model.BoardGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActionHistoryTest {

    private ActionHistory history;

    @BeforeEach
    void setUp() {
        history = new ActionHistory();
    }

    // ── isEmpty

    @Test
    void isEmpty_shouldReturnTrue_whenHistoryIsEmpty() {
        // Arrange — historique vide depuis setUp()

        // Act
        boolean result = history.isEmpty();

        // Assert
        assertTrue(result);
    }

    @Test
    void isEmpty_shouldReturnFalse_afterPush() {
        // Arrange
        UndoableAction<BoardGame> action = mock(UndoableAction.class);

        // Act
        history.push(action);

        // Assert
        assertFalse(history.isEmpty());
    }

    // ── size

    @Test
    void size_shouldReturnZero_whenHistoryIsEmpty() {
        // Arrange — historique vide

        // Act
        int size = history.size();

        // Assert
        assertEquals(0, size);
    }

    @Test
    void size_shouldReturnOne_afterOnePush() {
        // Arrange
        UndoableAction<BoardGame> action = mock(UndoableAction.class);
        history.push(action);

        // Act
        int size = history.size();

        // Assert
        assertEquals(1, size);
    }

    @Test
    void size_shouldIncrement_afterEachPush() {
        // Arrange
        UndoableAction<BoardGame> a1 = mock(UndoableAction.class);
        UndoableAction<BoardGame> a2 = mock(UndoableAction.class);
        history.push(a1);
        history.push(a2);

        // Act
        int size = history.size();

        // Assert
        assertEquals(2, size);
    }

    // ── push / pop

    @Test
    void pop_shouldReturnTheLastPushedAction() {
        // Arrange
        UndoableAction<BoardGame> action = mock(UndoableAction.class);
        history.push(action);

        // Act
        UndoableAction<BoardGame> popped = history.pop();

        // Assert
        assertSame(action, popped);
    }

    @Test
    void pop_shouldEmptyHistory_afterSinglePop() {
        // Arrange
        UndoableAction<BoardGame> action = mock(UndoableAction.class);
        history.push(action);

        // Act
        history.pop();

        // Assert
        assertTrue(history.isEmpty());
    }

    @Test
    void pop_shouldRespectLIFOOrder() {
        // Arrange
        UndoableAction<BoardGame> first = mock(UndoableAction.class);
        UndoableAction<BoardGame> second = mock(UndoableAction.class);
        history.push(first);
        history.push(second);

        // Act
        UndoableAction<BoardGame> popped = history.pop();

        // Assert
        assertSame(second, popped);
    }

    @Test
    void pop_shouldThrowException_whenHistoryIsEmpty() {
        // Arrange — historique vide

        // Act & Assert
        assertThrows(Exception.class, history::pop);
    }
}