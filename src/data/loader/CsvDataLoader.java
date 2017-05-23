package data.loader;


import api.entity.Group;
import api.entity.Root;
import model.tree.RootImpl;
import model.tree.StudentImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class CsvDataLoader extends FileDataLoader {
    CsvDataLoader(File file) {
        super(file);
    }

    @Override
    public Root loadData() throws IOException {
        Root root = new RootImpl();
        Files.lines(file.toPath()).forEach(s -> {
            String[] split = s.split(";");
            String lastName = split[0];
            String firstName = split[1];
            String middleName = split[2];
            int rating = Integer.parseInt(split[3]);
            Group group = root.getOrAddGroup(split[4]);
            boolean head = "head".equals(split[5]);
            group.addStudent(new StudentImpl(firstName, lastName, middleName, rating, group, head));
        });
        return root;
    }
}
