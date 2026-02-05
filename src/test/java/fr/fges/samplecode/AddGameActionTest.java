package fr.fges.samplecode;

import fr.fges.action.AddGameAction;
import fr.fges.service.GameService;
import fr.fges.ui.UserInput;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AddGameActionTest {

    @Test
    void execute_shouldAddGame_whenGameDoesNotExist() {
        // Arrange
        GameService service = mock(GameService.class);
        UserInput input = mock(UserInput.class);

        when(input.getString("Title")).thenReturn("Catan");
        when(service.gameExists("Catan")).thenReturn(false);
        when(input.getIntAtLeast("Minimum Players", 1)).thenReturn(3);
        when(input.getIntAtLeast("Maximum Players", 3)).thenReturn(4);
        when(input.getString(anyString())).thenReturn("Strategy");

        AddGameAction action = new AddGameAction(service, input);

        // Act
        action.execute();

        // Assert
        verify(service).addGame(any());
    }
}