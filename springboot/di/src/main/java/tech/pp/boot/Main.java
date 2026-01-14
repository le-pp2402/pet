package tech.pp.boot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tech.pp.core.MyContext;
import tech.pp.demo0.service.UserService;
import tech.pp.dispatch.Dispatcher;
import tech.pp.web.server.TomPhatServer;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args)
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        log.info("Inititializing bean context...");

        var context = new MyContext("tech.pp.demo0");
        UserService userService = context.getBean(UserService.class);
        userService.save();

        Dispatcher.loadControllers();
        (new TomPhatServer()).start();
    }
}