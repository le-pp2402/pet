package tech.pp.dispatch;

import java.util.HashMap;

import org.apache.logging.log4j.Logger;

import tech.pp.core.MyContext;
import tech.pp.core.annotation.MyGetMapping;
import tech.pp.core.annotation.MyRestController;
import tech.pp.web.http.HttpMethod;

public class Dispatcher {
    private static Logger log = org.apache.logging.log4j.LogManager.getLogger(Dispatcher.class);
    private static HashMap<String, HandlerMethod> handlerRegistry = new HashMap<>();

    public static void loadControllers() {
        for (Object instance : MyContext.beanMap.values()) {
            if (!instance.getClass().isAnnotationPresent(MyRestController.class)) {
                continue;
            }
            log.info("Loaded controller: " + instance.getClass().getName());
            var methods = instance.getClass().getDeclaredMethods();
            for (var m : methods) {
                if (m.isAnnotationPresent(MyGetMapping.class)) {
                    var handlerMethod = new HandlerMethod(instance, m);
                    var path = "GET:" + m.getAnnotation(MyGetMapping.class).value();
                    log.info("Mapping {} to {}", path, handlerMethod);
                    handlerRegistry.put(path, handlerMethod);
                }
            }
        }
    }

    public static HandlerMethod dispatch(HttpMethod method, String path) {
        return handlerRegistry.get(method + ":" + path);
    }
}
