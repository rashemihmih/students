package data.loader;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class DataLoaderFactory {
    public FileDataLoader getDataLoader(File file) {
        String extension = StringUtils.substringAfterLast(file.getName(), ".");
        if ("json".equalsIgnoreCase(extension)) {
            return new JsonDataLoader(file);
        } else if ("xml".equalsIgnoreCase(extension)) {
            return new XmlDataLoader(file);
        } else if ("csv".equalsIgnoreCase(extension)) {
            return new CsvDataLoader(file);
        }
        return null;
    }
}
