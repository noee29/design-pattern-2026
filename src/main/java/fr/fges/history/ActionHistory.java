package fr.fges.history;

import fr.fges.action.UndoableAction;

import java.util.Stack;

public class ActionHistory {

    private final Stack<UndoableAction> stack = new Stack<>();

    public void push(UndoableAction action) {
        stack.push(action);
    }

    public UndoableAction pop() {
        return stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }
}