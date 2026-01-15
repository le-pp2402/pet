package tech.pp.dispatch;

import java.util.HashMap;

import org.apache.logging.log4j.Logger;

import tech.pp.core.MyContext;
import tech.pp.core.annotation.PGetMapping;
import tech.pp.core.annotation.PRestController;
import tech.pp.web.http.HttpMethod;

public class Dispatcher {
    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(Dispatcher.class);
    private static final HashMap<String, HandlerMethod> handlerRegistry = new HashMap<>();

    public static void loadControllers(MyContext context) {
        for (Object instance : context.beanMap.values()) {
            if (!instance.getClass().isAnnotationPresent(PRestController.class)) {
                continue;
            }
            log.info("Loaded controller: " + instance.getClass().getName());
            var methods = instance.getClass().getDeclaredMethods();
            for (var m : methods) {
                if (m.isAnnotationPresent(PGetMapping.class)) {
                    var handlerMethod = new HandlerMethod(instance, m);
                    var path = "GET:" + m.getAnnotation(PGetMapping.class).value();
                    log.info("Mapping {} to {}", path,
                            handlerMethod.getBean().getClass().getName() + "." + handlerMethod.getMethod().getName());
                    handlerRegistry.put(path, handlerMethod);
                }
            }
        }
    }

    public static HandlerMethod dispatch(HttpMethod method, String path) {
        return handlerRegistry.get(method + ":" + path);
    }
}
