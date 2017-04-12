package model.command;

import model.Command;
import model.tree.Group;
import model.tree.Root;

public class DeleteGroupCommand implements Command {
    private Root root;
    private Group group;

    public DeleteGroupCommand(Root root, Group group) {
        this.root = root;
        this.group = group;
    }

    @Override
    public void execute() {
        root.removeGroup(group);
    }

    @Override
    public void rollback() {
        root.addGroup(group);
    }
}
