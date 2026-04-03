package fr.fges.businesslogic;

import fr.fges.ui.MenuEntry;

/** Interface pour les actions annulables : étend MenuEntry et ajoute la méthode undo(). */
public interface UndoableAction<T> extends MenuEntry {
    void undo();
}