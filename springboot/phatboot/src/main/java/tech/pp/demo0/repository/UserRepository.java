package tech.pp.demo0.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tech.pp.core.annotation.PComponent;
import tech.pp.demo0.model.User;

@PComponent
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

    public List<User> findAllUsers() {
        log.info("fetching all users from db");
        List<User> users = new ArrayList<>();
        users.add(new User("1", "Alice"));
        users.add(new User("2", "Bob"));
        return users;
    }
}
