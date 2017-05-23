package model.command;

import api.entity.Student;
import model.Command;

public class ToggleHeadCommand implements Command {
    private Student student;

    public ToggleHeadCommand(Student student) {
        this.student = student;
    }

    @Override
    public void execute() {
        student.setHead(!student.isHead());
    }

    @Override
    public void rollback() {
        execute();
    }
}
