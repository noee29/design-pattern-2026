package fr.fges.businesslogic;

import fr.fges.ui.MenuEntry;

public interface UndoableAction<T> extends MenuEntry {
    void undo();
}