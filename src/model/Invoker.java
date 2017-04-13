package model;

import java.util.ArrayDeque;
import java.util.Deque;

public class Invoker {
    private static final int LIMIT = 100;
    private Deque<Command> done = new ArrayDeque<>();
    private Deque<Command> undone = new ArrayDeque<>();

    public boolean canUndo() {
        return !done.isEmpty();
    }

    public boolean canRedo() {
        return !undone.isEmpty();
    }

    public void execute(Command command) {
        command.execute();
        done.push(command);
        if (done.size() > LIMIT) {
            done.pollLast();
        }
        undone.clear();
    }

    public void undo() {
        if (!canUndo()) {
            return;
        }
        Command command = done.pop();
        command.rollback();
        undone.push(command);
        if (undone.size() > LIMIT) {
            undone.pollLast();
        }
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
