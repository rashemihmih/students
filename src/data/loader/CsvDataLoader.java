package data.loader;

import model.tree.Group;
import model.tree.Root;
import model.tree.Student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class CsvDataLoader extends FileDataLoader {
    CsvDataLoader(File file) {
        super(file);
    }

    @Override
    public Root loadData() throws IOException {
        Root root = new Root();
        Files.lines(file.toPath()).forEach(s -> {
            String[] split = s.split(";");
            String lastName = split[0];
            String firstName = split[1];
            String middleName = split[2];
            int rating = Integer.parseInt(split[3]);
            Group group = root.getOrAddGroup(split[4]);
            boolean head = "head".equals(split[5]);
            group.addStudent(new Student(firstName, lastName, middleName, rating, group, head));
        });
        return root;
    }
}
