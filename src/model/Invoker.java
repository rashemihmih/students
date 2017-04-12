package model;

import java.util.Stack;

public class Invoker {
    private Stack<Command> done = new Stack<>();
    private Stack<Command> undone = new Stack<>();

    public boolean canUndo() {
        return !done.empty();
    }

    public boolean canRedo() {
        return !undone.empty();
    }

    public void execute(Command command) {
        command.execute();
        done.push(command);
        undone.clear();
    }

    public void undo() {
        if (!canUndo()) {
            return;
        }
        Command command = done.pop();
        command.rollback();
        undone.push(command);
    }

    public void redo() {
        if (!canRedo()) {
            return;
        }
        Command command = undone.pop();
        command.execute();
        done.push(command);
    }
}
