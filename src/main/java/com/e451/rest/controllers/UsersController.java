package com.e451.rest.controllers;

import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.domains.user.UserVerification;
import com.e451.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by l659598 on 6/20/2017.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody User user) { return userService.updateUser(user); }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserVerification userVerification) {
        return userService.updateUser(userVerification);
    }

    @GetMapping("/activate/{guid}")
    public ResponseEntity activateUser(@PathVariable("guid") String guid) {
        return userService.activate(guid);
    }

    @GetMapping("/activeUser")
    public ResponseEntity<UserResponse> getActiveUer() { return userService.getActiveUser(); }

}

