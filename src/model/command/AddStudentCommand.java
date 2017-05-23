package model.command;

import api.entity.Student;
import model.Command;

public class AddStudentCommand implements Command {
    private Student student;

    public AddStudentCommand(Student student) {
        this.student = student;
    }

    @Override
    public void execute() {
        student.getGroup().addStudent(student);
    }

    @Override
    public void rollback() {
        student.getGroup().removeStudent(student);
    }
}
