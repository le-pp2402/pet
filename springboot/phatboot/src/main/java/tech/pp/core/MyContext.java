package tech.pp.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tech.pp.core.annotation.PComponent;
import tech.pp.core.annotation.PInject;
import tech.pp.core.annotation.PRestController;
import tech.pp.core.scanner.ClassScanner;
import tech.pp.core.util.graph.CycleGraphDetector;
import tech.pp.core.util.graph.Edge;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyContext {
    private static final Logger log = LogManager.getLogger(MyContext.class);
    public Map<Class<?>, Object> beanMap = new HashMap<Class<?>, Object>();

    public MyContext(String pkgName)
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {

        List<Class<?>> classes = new ArrayList<>();
        try {
            classes = ClassScanner.scan(pkgName);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        for (var clazz : classes) {
            if (clazz.isAnnotationPresent(PComponent.class) || clazz.isAnnotationPresent(PRestController.class)) {
                if (clazz.getDeclaredConstructors().length == 0) {
                    log.error("No constructor found for class {}", clazz.getName());
                    throw new RuntimeException();
                } else {
                    Object instance = clazz.getDeclaredConstructors()[0].newInstance();
                    beanMap.put(clazz, instance);
                }
            }
        }

        List<Edge<Integer>> edges = Collections.synchronizedList(new ArrayList<>());

        for (Object instance : beanMap.values()) {
            for (Field field : instance.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(PInject.class)) {
                    Object dependency = beanMap.get(field.getType());
                    if (dependency != null) {
                        field.setAccessible(true);
                        field.set(instance, dependency);
                        edges.add(new Edge<Integer>() {
                            {
                                from = instance.hashCode();
                                to = dependency.hashCode();
                            }
                        });
                    }
                }
            }
        }

        var detector = new CycleGraphDetector<Integer>(edges);
        if (detector.hasCycle()) {
            log.error("Cycle detected in dependency graph");
            throw new RuntimeException("Cycle detected in dependency graph");
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beanMap.get(clazz));
    }
}
