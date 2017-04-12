package data.saver;

import model.tree.Root;

import java.io.File;
import java.io.IOException;

public interface DataSaver {
    void saveData(Root root, File file) throws IOException;
}
