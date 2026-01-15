package tech.pp.boot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tech.pp.core.MyContext;
import tech.pp.dispatch.Dispatcher;
import tech.pp.web.server.TomPhatServer;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class PhatBoot {
    private static final Logger log = LogManager.getLogger(PhatBoot.class);

    public static void run(String packageName)
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        log.info("  ____  _           _   ____              _   \n" +
                " |  _ \\| |__   __ _| |_| __ )  ___   ___ | |_ \n" +
                " | |_) | '_ \\ / _` | __|  _ \\ / _ \\ / _ \\| __|\n" +
                " |  __/| | | | (_| | |_| |_) | (_) | (_) | |_ \n" +
                " |_|   |_| |_|\\__,_|\\__|____/ \\___/ \\___/ \\__|\n");

        log.info("Implement by: https://github.com/le-pp2402");
        log.info("\n\n\n");
        log.info("Inititializing bean context...");
        var context = new MyContext(packageName);
        Dispatcher.loadControllers(context);
        (new TomPhatServer()).start();
    }
}