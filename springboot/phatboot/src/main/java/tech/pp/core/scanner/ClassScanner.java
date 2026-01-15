package tech.pp.core.scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ClassScanner {
    private final static Logger log = LogManager.getLogger();

    /*
     * Get all classes from dictionary
     */
    private static List<Class<?>> loadClasses(Path rootPath, String packageName) throws IOException {
        log.info("Loading classes in path: {}", rootPath.toString());
        List<Class<?>> classes = new ArrayList<Class<?>>();
        try (Stream<Path> stream = Files.walk(rootPath)) {
            stream.filter(t -> t.toString().endsWith(".class")).forEach(
                    t -> {
                        String className = rootPath.relativize(t).toString()
                                .replace(File.separator, ".")
                                .replace(".class", "");

                        while (className.startsWith(".")) {
                            className = className.substring(1);
                        }

                        log.info("Found classes: {}", className);

                        try {
                            Class<?> clazz = Class.forName(packageName + "." + className);
                            classes.add(clazz);
                        } catch (ClassNotFoundException ex) {
                            log.error("ClassNotFoundException {}", packageName + "." + className);
                        }
                    });
        } catch (Exception e) {
            log.error("Error loading classes from path: " + rootPath.toString(), e);
            e.printStackTrace();
        }
        return classes;
    }

    public static List<Class<?>> scan(String packageName) throws URISyntaxException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');

        URL resource = classLoader.getResource(path);

        if (resource == null) {
            return new ArrayList<>();
        }

        log.info("Loading package {} with resource scheme: {}", packageName, resource.toURI().getScheme());

        if (resource.toURI().getScheme().startsWith("jar")) {
            // TODO: thêm chức năng đọc env và load vào jar
            var env = new java.util.HashMap<String, String>();

            try (var fs = FileSystems.newFileSystem(resource.toURI(), env)) {
                Path rootPath = fs.getPath(path);
                return loadClasses(rootPath, packageName);
            }
        } else if (resource.toURI().getScheme().startsWith("file")) {
            Path rootPath = Path.of(resource.toURI());
            return loadClasses(rootPath, packageName);
        } else {
            throw new RuntimeException("Unsupported resource scheme: " + resource.toURI().getScheme());
        }
    }
}
