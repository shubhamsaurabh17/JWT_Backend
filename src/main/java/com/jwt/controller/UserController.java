package com.jwt.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.entity.User;
import com.jwt.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/register"})
    public ResponseEntity<?> registerNewUser(@RequestBody User user) {
//        return userService.registerNewUser(user);
        try {
            userService.registerNewUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping({"/admin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "Welcome Admin";
    }

    @GetMapping({"/user"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "Welcome User";
    }
    
    @GetMapping({"/helloWorld"})
    public String helloWorld() {
    	return "Hello World";
    }
}
