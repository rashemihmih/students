package data.saver;


import api.entity.Root;

import java.io.File;
import java.io.IOException;

public interface DataSaver {
    void saveData(Root root, File file) throws IOException;
}
