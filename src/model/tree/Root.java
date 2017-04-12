package model.tree;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Root implements TreeElement {
    private List<Group> groups = new ArrayList<>();

    public List<Group> getGroups() {
        Collections.sort(groups);
        return groups;
    }

    public boolean containsGroup(Group group) {
        return groups.contains(group);
    }

    public boolean containsGroup(String name) {
        return groups.stream().anyMatch(group -> group.getName().equals(StringUtils.trim(name)));
    }

    public Group getOrAddGroup(String name) {
        final String trim = StringUtils.trim(name);
        return groups.stream().filter(group -> group.getName().equals(trim)).findAny().orElseGet(() -> {
            Group group = new Group(trim);
            groups.add(group);
            return group;
        });
    }

    public void addGroup(Group group) {
        if (containsGroup(group)) {
            return;
        }
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public Group getGroup(String name) {
        return groups.stream().filter(group -> group.getName().equals(StringUtils.trim(name))).findAny().orElse(null);
    }

    @Override
    public Type getType() {
        return Type.ROOT;
    }

    @Override
    public String toString() {
        return "Группы";
    }
}
