package model.command;

import model.Command;
import model.tree.Student;

public class DeleteStudentCommand implements Command {
    private Student student;

    public DeleteStudentCommand(Student student) {
        this.student = student;
    }

    @Override
    public void execute() {
        student.getGroup().removeStudent(student);
    }

    @Override
    public void rollback() {
        student.getGroup().addStudent(student);
    }
}
