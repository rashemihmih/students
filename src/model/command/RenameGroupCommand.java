package model.command;

import model.Command;
import model.tree.Group;

public class RenameGroupCommand implements Command {
    private Group group;
    private String name;

    public RenameGroupCommand(Group group, String name) {
        this.group = group;
        this.name = name;
    }

    @Override
    public void execute() {
        String oldName = group.getName();
        group.setName(name);
        name = oldName;
    }

    @Override
    public void rollback() {
        execute();
    }
}
