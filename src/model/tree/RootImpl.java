package model.tree;

import api.entity.Group;
import api.entity.Root;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RootImpl implements Root {
    private List<Group> groups = new ArrayList<>();

    @Override
    public List<Group> getGroups() {
        Collections.sort(groups);
        return groups;
    }

    @Override
    public boolean containsGroup(Group group) {
        return groups.contains(group);
    }

    @Override
    public boolean containsGroup(String name) {
        return groups.stream().anyMatch(group -> group.getName().equals(StringUtils.trim(name)));
    }

    @Override
    public Group getOrAddGroup(String name) {
        final String trim = StringUtils.trim(name);
        return groups.stream().filter(group -> group.getName().equals(trim)).findAny().orElseGet(() -> {
            Group group = new GroupImpl(trim);
            groups.add(group);
            return group;
        });
    }

    @Override
    public void addGroup(Group group) {
        if (containsGroup(group)) {
            return;
        }
        groups.add(group);
    }

    @Override
    public void removeGroup(Group group) {
        groups.remove(group);
    }

    @Override
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
