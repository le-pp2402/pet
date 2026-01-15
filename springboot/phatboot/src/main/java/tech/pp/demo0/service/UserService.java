package tech.pp.demo0.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tech.pp.core.annotation.PComponent;
import tech.pp.core.annotation.PInject;
import tech.pp.demo0.model.User;
import tech.pp.demo0.repository.UserRepository;

@PComponent
public class UserService {
    @PInject
    private UserRepository userRepository;

    private Logger log = LogManager.getLogger(UserService.class);

    public void save() {
        userRepository.connection();
        log.info("saving user");
    }

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }
}
