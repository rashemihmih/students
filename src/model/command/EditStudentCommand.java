package model.command;

import model.Command;
import model.tree.Group;
import model.tree.Root;
import model.tree.Student;
import org.apache.commons.lang3.StringUtils;

public class EditStudentCommand implements Command {
    private Root root;
    private Student student;
    private String firstName;
    private String lastName;
    private String middleName;
    private int rating;
    private String groupName;

    public EditStudentCommand(Root root, Student student, String firstName, String lastName, String middleName,
                              int rating, String groupName) {
        firstName = StringUtils.trim(firstName);
        lastName = StringUtils.trim(lastName);
        middleName = StringUtils.trim(middleName);
        groupName = StringUtils.trim(groupName);
        if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName) || StringUtils.isEmpty(middleName)
                || StringUtils.isEmpty(groupName)) {
            throw new IllegalArgumentException("Фамилия, имя, отчество и группа не должны быть пустыми");
        }
        this.root = root;
        this.student = student;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.rating = rating;
        this.groupName = groupName;
    }

    @Override
    public void execute() {
        if (!student.getFirstName().equals(firstName)) {
            String old = student.getFirstName();
            student.setFirstName(firstName);
            firstName = old;
        }
        if (!student.getLastName().equals(lastName)) {
            String old = student.getLastName();
            student.setLastName(lastName);
            lastName = old;
        }
        if (!student.getMiddleName().equals(middleName)) {
            String old = student.getMiddleName();
            student.setMiddleName(middleName);
            middleName = old;
        }
        if (student.getRating() != rating) {
            int old = student.getRating();
            student.setRating(rating);
            rating = old;
        }
        if (!student.getGroup().getName().equals(groupName)) {
            Group group = root.getGroup(groupName);
            if (group == null) {
                throw new IllegalArgumentException("Указанной группы не существует");
            }
            Group old = student.getGroup();
            old.removeStudent(student);
            group.addStudent(student);
            student.setGroup(group);
            groupName = old.getName();
        }
    }

    @Override
    public void rollback() {
        execute();
    }
}
