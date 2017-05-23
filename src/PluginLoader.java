import api.Plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class PluginLoader {
    public static List<Plugin> loadPlugins(File pluginDir) {
        List<Plugin> plugins = new ArrayList<>();
        File[] jars = pluginDir.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        if (jars == null) {
            return plugins;
        }
        for (File jar : jars) {
            try {
                ClassLoader classLoader = new URLClassLoader(new URL[]{jar.toURI().toURL()});
                JarFile jarFile = new JarFile(jar);
                plugins.addAll(jarFile.stream().filter(e -> e.getName().endsWith(".class"))
                        .map(e -> e.getName().replaceAll("/", ".").replaceAll(".class", ""))
                        .map(s -> {
                            try {
                                return classLoader.loadClass(s);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }).filter(Objects::nonNull).filter(clazz -> {
                            for (Class i : clazz.getInterfaces()) {
                                if (i.getName().endsWith("." + Plugin.class.getSimpleName())) {
                                    return true;
                                }
                            }
                            return false;
                        }).map(clazz -> {
                            try {
                                return (Plugin) clazz.newInstance();
                            } catch (InstantiationException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }).filter(Objects::nonNull).collect(Collectors.toList()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return plugins;
    }
}
