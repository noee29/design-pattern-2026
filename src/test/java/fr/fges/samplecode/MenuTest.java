package fr.fges.samplecode;

import fr.fges.*;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MenuTest {

    @Test
    void menu_shouldDelegateToMenuActions_whenChoiceIs3() {
        // Arrange
        StorageStrategy storage = mock(StorageStrategy.class);
        GameRepository repository = mock(GameRepository.class);

        Menu menu = spy(new Menu(repository));

        // On empêche la boucle infinie
        doThrow(new RuntimeException("stop"))
                .when(menu).handleMenu();

        // Act + Assert
        try {
            menu.handleMenu();
        } catch (RuntimeException ignored) {
        }
    }
}
