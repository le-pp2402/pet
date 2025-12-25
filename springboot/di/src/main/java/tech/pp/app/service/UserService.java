package tech.pp.app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.pp.app.repository.UserRepository;
import tech.pp.myframework.anotations.MyComponent;
import tech.pp.myframework.anotations.MyInject;

@MyComponent
public class UserService {
    @MyInject
    private UserRepository userRepository;

    private Logger log = LogManager.getLogger(UserService.class);

    public void save() {
        userRepository.connection();
        log.info("saving user");
    }
}
