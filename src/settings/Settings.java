package settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Settings {
    private static final Path PATH = Paths.get(System.getProperty("user.home") + "/.students/settings.txt");
    private static int fontSize = 11;

    public static int getFontSize() {
        return fontSize;
    }

    public static void setFontSize(int fontSize) {
        Settings.fontSize = fontSize;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void save() {
        Properties properties = new Properties();
        properties.setProperty("fontSize", String.valueOf(fontSize));
        PATH.getParent().toFile().mkdirs();
        try (BufferedWriter writer = Files.newBufferedWriter(PATH)) {
            properties.store(writer, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try (BufferedReader reader = Files.newBufferedReader(PATH)) {
            Properties properties = new Properties();
            properties.load(reader);
            fontSize = Integer.parseInt(properties.getProperty("fontSize"));
        } catch (IOException | NumberFormatException ignore) {
        }
    }
}
