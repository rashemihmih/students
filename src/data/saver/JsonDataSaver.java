package data.saver;

import api.entity.Group;
import api.entity.Root;
import api.entity.Student;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JsonDataSaver implements DataSaver {
    @Override
    public void saveData(Root root, File file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
            JsonArray array = new JsonArray();
            for (Group group : root.getGroups()) {
                for (Student student : group.getStudents()) {
                    JsonObject object = new JsonObject();
                    object.addProperty("Surname", student.getLastName());
                    object.addProperty("Name", student.getFirstName());
                    object.addProperty("SecondName", student.getMiddleName());
                    object.addProperty("Rating", student.getRating());
                    object.addProperty("Group", student.getGroup().getName());
                    object.addProperty("Role", student.isHead() ? "head" : "student");
                    array.add(object);
                }
            }
            writer.write(array.toString());
        }
    }
}
