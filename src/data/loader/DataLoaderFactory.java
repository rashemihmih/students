package data.loader;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class DataLoaderFactory {
    public FileDataLoader getDataLoader(File file) {
        String extenstion = StringUtils.substringAfterLast(file.getName(), ".");
        if ("json".equalsIgnoreCase(extenstion)) {
            return new JsonDataLoader(file);
        } else if ("xml".equalsIgnoreCase(extenstion)) {
            return new XmlDataLoader(file);
        } else if ("csv".equalsIgnoreCase(extenstion)) {
            return new CsvDataLoader(file);
        }
        return null;
    }
}
