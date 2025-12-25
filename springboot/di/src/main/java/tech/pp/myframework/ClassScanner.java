package tech.pp.myframework;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClassScanner {
    private final static Logger log = LogManager.getLogger();
    public static List<Class<?>> scan(String pkg) throws URISyntaxException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = pkg.replace('.', '/');

        URL resource = classLoader.getResource(path);

        if (resource == null) {
            log.error("resource not found");
            return classes;
        }

        var rootDir = new File(Objects.requireNonNull(classLoader.getResource("")).toURI());
        String rootPath = rootDir.getAbsolutePath();

        log.info("Found: {}", resource.getFile());

        File baseDir = new File(resource.getFile());
        Queue<File> qu = new LinkedBlockingQueue<>();
        qu.add(baseDir);

        while (!qu.isEmpty()) {
            var elem = qu.poll();

            if (elem == null) {
                continue;
            }

            for (var file: Objects.requireNonNull(elem.listFiles())) {
                if (file.isDirectory()) {
                    qu.add(file);
                } else if (file.getName().endsWith(".class")) {
                    String absolutePath = file.getAbsolutePath();

                    String className = absolutePath
                            .substring(rootPath.length() + 1)
                            .replace(File.separator, ".")
                            .replace(".class", "");

                    log.info("Found files: {}", className);

                    try {
                        Class<?> clazz = Class.forName(className);
                        classes.add(clazz);
                    } catch (ClassNotFoundException ex) {
                        log.error("ClassNotFoundException {}", ex.getMessage());
                    }
                }
            }
        }

        return classes;
    }
}
