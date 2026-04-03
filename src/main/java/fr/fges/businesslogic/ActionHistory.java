package fr.fges.businesslogic;

import fr.fges.model.BoardGame;

import java.util.Stack;

/** Gestionnaire de l'historique des actions annulables : maintient une pile d'UndoableAction pour la fonctionnalité undo. */
public class ActionHistory {

    private final Stack<UndoableAction<BoardGame>> stack = new Stack<>();

    public void push(UndoableAction<BoardGame> action) {
        stack.push(action);
    }

    public UndoableAction<BoardGame> pop() {
        return stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }
}