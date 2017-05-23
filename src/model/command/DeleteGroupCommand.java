package model.command;

import api.entity.Group;
import api.entity.Root;
import model.Command;

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
