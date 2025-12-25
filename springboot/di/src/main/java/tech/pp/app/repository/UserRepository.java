package tech.pp.app.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.pp.myframework.anotations.MyComponent;

@MyComponent
public class UserRepository {
    private final Logger log = LogManager.getLogger(UserRepository.class);
    public void connection() {
        log.info("connecting to db");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("connected to db");
    }
}
