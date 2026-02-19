package fr.fges.businesslogic;

import fr.fges.action.MenuAction;

public interface UndoableAction<T> extends MenuAction {
    void undo();
}