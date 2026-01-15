package tech.pp.demo0.controller;

import java.util.List;

import tech.pp.core.annotation.PGetMapping;
import tech.pp.core.annotation.PInject;
import tech.pp.core.annotation.PRestController;
import tech.pp.demo0.model.User;
import tech.pp.demo0.service.UserService;
import tech.pp.web.http.HttpResponse;
import tech.pp.web.http.ResponseHeader;

@PRestController
public class UserController {

    @PInject
    private UserService userService;

    @PGetMapping("/users")
    public HttpResponse<List<User>> getAllUsers() {
        var header = ResponseHeader.newBuilder()
                .statusCode(200)
                .contentType("text/json")
                .build();

        var users = userService.getAllUsers();

        return new HttpResponse<>(header, users);
    }
}