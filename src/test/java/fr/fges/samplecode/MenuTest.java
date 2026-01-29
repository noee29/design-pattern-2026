package fr.fges.samplecode;

import fr.fges.GamePrinter;
import fr.fges.GameRepository;
import fr.fges.Menu;
import fr.fges.MenuActions;
import fr.fges.StorageStrategy;
import fr.fges.UserInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * Unit tests for Menu class using AAA pattern.
 */
class MenuTest {

    @Test
    void handleMenu_shouldNotCrashWhenExitChosen() throws Exception {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        when(storage.load()).thenReturn(java.util.List.of());

        GameRepository repository = new GameRepository(storage);

        // Spy MenuActions to intercept exit
        MenuActions actions = spy(new MenuActions(
                mock(UserInput.class),
                repository,
                new GamePrinter()
        ));

        doNothing().when(actions).exitApplication();

        // Spy Menu to inject our actions
        Menu menu = spy(new Menu(repository));
        doReturn(actions).when(menu).getActionsForTest();

        // Act & Assert
        assertDoesNotThrow(menu::handleMenu);
    }
}
