package data.loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.tree.Group;
import model.tree.Root;
import model.tree.Student;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class JsonDataLoader extends FileDataLoader {
    JsonDataLoader(File file) {
        super(file);
    }

    @Override
    public Root loadData() throws IOException {
        Root root = new Root();
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            JsonArray array = new JsonParser().parse(reader).getAsJsonArray();
            for (JsonElement element : array) {
                JsonObject object = element.getAsJsonObject();
                String lastName = object.get("Surname").getAsString();
                String firstName = object.get("Name").getAsString();
                String middleName = object.get("SecondName").getAsString();
                int rating = object.get("Rating").getAsInt();
                Group group = root.getOrAddGroup(object.get("Group").getAsString());
                boolean head = "head".equals(object.get("Role").getAsString());
                group.addStudent(new Student(firstName, lastName, middleName, rating, group, head));
            }
        }
        return root;
    }
}
