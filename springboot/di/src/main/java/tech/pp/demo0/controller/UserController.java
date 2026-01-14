package tech.pp.demo0.controller;

import java.util.List;

import tech.pp.core.annotation.MyGetMapping;
import tech.pp.core.annotation.MyInject;
import tech.pp.core.annotation.MyRestController;
import tech.pp.demo0.service.UserService;

@MyRestController
public class UserController {

    @MyInject
    private UserService userService;

    @MyGetMapping("/users")
    public List<String> getAllUsers() {
        return userService.getAllUsers();
    }
}