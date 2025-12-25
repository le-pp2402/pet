package tech.pp.myframework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.pp.myframework.anotations.MyComponent;
import tech.pp.myframework.anotations.MyInject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyContext {
    private static final Logger log = LogManager.getLogger(MyContext.class);
    private Map<Class<?>, Object> beanMap = new HashMap();

    public MyContext(String pkgName) throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Class<?>> classes = ClassScanner.scan(pkgName);

        for(var clazz: classes) {
            if (clazz.isAnnotationPresent(MyComponent.class)) {
                if (clazz.getDeclaredConstructors().length == 0) {
                    log.error("No constructor found for class {}", clazz.getName());
                    throw new RuntimeException();
                } else {
                    Object instance = clazz.getDeclaredConstructors()[0].newInstance();
                    beanMap.put(clazz, instance);
                }
            }
        }

        for (Object instance : beanMap.values()) {
            for (Field field : instance.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(MyInject.class)) {
                    Object dependency = beanMap.get(field.getType());
                    if (dependency != null) {
                        field.setAccessible(true);
                        field.set(instance, dependency);
                    }
                }
            }
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beanMap.get(clazz));
    }
}
