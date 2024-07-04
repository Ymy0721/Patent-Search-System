package org.example.ui;

import java.util.Stack;

public class UndoManager {
    private final Stack<Runnable> undoStack = new Stack<>();
    private final Stack<Runnable> redoStack = new Stack<>();

    public void doAction(Runnable action, Runnable undoAction) {
        action.run();
        undoStack.push(undoAction);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Runnable undoAction = undoStack.pop();
            undoAction.run();
            redoStack.push(undoAction);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Runnable redoAction = redoStack.pop();
            redoAction.run();
            undoStack.push(redoAction);
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}