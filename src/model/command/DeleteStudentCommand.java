package model.command;

import api.entity.Student;
import model.Command;

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
