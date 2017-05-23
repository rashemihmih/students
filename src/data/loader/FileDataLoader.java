package data.loader;

import api.entity.Root;

import java.io.File;
import java.io.IOException;

public abstract class FileDataLoader {
    protected File file;

    public FileDataLoader(File file) {
        this.file = file;
    }

    public abstract Root loadData() throws IOException;
}
