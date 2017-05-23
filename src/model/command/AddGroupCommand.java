package model.command;

import api.entity.Group;
import api.entity.Root;
import model.Command;

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
