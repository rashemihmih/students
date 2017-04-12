package model.command;

import model.Command;
import model.tree.Student;

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
