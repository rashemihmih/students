package model.tree;

import api.entity.Group;
import api.entity.Student;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("ComparableImplementedButEqualsNotOverridden")
public class StudentImpl implements Student {
    public static final int MIN_RATING = 0;
    public static final int MAX_RATING = 100;
    private String firstName;
    private String lastName;
    private String middleName;
    private int rating;
    private Group group;
    private boolean head;

    public StudentImpl(String firstName, String lastName, String middleName, int rating, Group group, boolean head) {
        firstName = StringUtils.trim(firstName);
        lastName = StringUtils.trim(lastName);
        middleName = StringUtils.trim(middleName);
        if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName) || StringUtils.isEmpty(middleName)) {
            throw new IllegalArgumentException("Фамилия, имя и отчество не должны быть пустыми");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.rating = limitRating(rating);
        this.group = group;
        this.head = head;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        firstName = StringUtils.trim(firstName);
        if (StringUtils.isEmpty(firstName)) {
            throw new IllegalArgumentException("Имя не должно быть пустым");
        }
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        lastName = StringUtils.trim(lastName);
        if (StringUtils.isEmpty(lastName)) {
            throw new IllegalArgumentException("Фамилия не должна быть пустой");
        }
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        middleName = StringUtils.trim(middleName);
        if (StringUtils.isEmpty(middleName)) {
            throw new IllegalArgumentException("Отчество не должно быть пустым");
        }
        this.middleName = middleName;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public void setRating(int rating) {
        this.rating = limitRating(rating);
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean isHead() {
        return head;
    }

    @Override
    public void setHead(boolean head) {
        this.head = head;
    }

    private int limitRating(int value) {
        if (value < MAX_RATING) {
            if (value > MIN_RATING) {
                return value;
            }
            return MIN_RATING;
        }
        return MAX_RATING;
    }

    @Override
    public String toString() {
        return lastName + ' ' + firstName + ' ' + middleName;
    }

    @Override
    public Type getType() {
        return Type.STUDENT;
    }

    @Override
    public int compareTo(Student o) {
        int lastNameDiff = lastName.compareTo(o.getLastName());
        if (lastNameDiff != 0) {
            return lastNameDiff;
        }
        int firstNameDiff = firstName.compareTo(o.getFirstName());
        if (firstNameDiff != 0) {
            return firstNameDiff;
        }
        return middleName.compareTo(o.getMiddleName());
    }
}
