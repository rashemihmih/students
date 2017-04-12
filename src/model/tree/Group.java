package model.tree;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group implements TreeElement, Comparable<Group> {
    private String name;
    private List<Student> students = new ArrayList<>();

    public Group(String name) {
        name = StringUtils.trim(name);
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Название группы не должно быть пустым");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        Collections.sort(students);
        return students;
    }

    public void setName(String name) {
        name = StringUtils.trim(name);
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Название группы не должно быть пустым");
        }
        this.name = name;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public int getStudentsCount() {
        return students.size();
    }

    public int getMaxRating() {
        return students.stream().mapToInt(Student::getRating).max().orElse(0);
    }

    public int getMinRating() {
        return students.stream().mapToInt(Student::getRating).min().orElse(0);
    }

    public double getAvgRating() {
        return students.stream().mapToInt(Student::getRating).average().orElse(0);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Type getType() {
        return Type.GROUP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return name.equals(group.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Group o) {
        return name.compareTo(o.name);
    }
}
