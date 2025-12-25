package tech.pp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.pp.app.service.UserService;
import tech.pp.myframework.MyContext;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        log.info("Application running");
        var context = new MyContext("tech.pp.app");

        UserService userRepo = context.getBean(UserService.class);
        userRepo.save();
    }
}