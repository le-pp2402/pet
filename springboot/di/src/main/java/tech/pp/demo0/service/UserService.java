package tech.pp.demo0.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tech.pp.core.annotation.MyComponent;
import tech.pp.core.annotation.MyInject;
import tech.pp.demo0.repository.UserRepository;

@MyComponent
public class UserService {
    @MyInject
    private UserRepository userRepository;

    private Logger log = LogManager.getLogger(UserService.class);

    public void save() {
        userRepository.connection();
        log.info("saving user");
    }

    public List<String> getAllUsers() {
        log.info("getting all users");
        return List.of("User1", "User2", "User3");
    }
}
