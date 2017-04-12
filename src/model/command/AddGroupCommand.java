package model.command;

import model.Command;
import model.tree.Group;
import model.tree.Root;

public class AddGroupCommand implements Command {
    private Root root;
    private Group group;

    public AddGroupCommand(Root root, Group group) {
        this.root = root;
        this.group = group;
    }

    @Override
    public void execute() {
        root.addGroup(group);
    }

    @Override
    public void rollback() {
        root.removeGroup(group);
    }
}
