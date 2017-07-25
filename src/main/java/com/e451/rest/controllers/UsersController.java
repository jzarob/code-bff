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

    @GetMapping
    public ResponseEntity<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(params = {"page", "size", "property"})
    public ResponseEntity<UserResponse> getUsers(@RequestParam("page") int page,
                                                 @RequestParam("size") int size,
                                                 @RequestParam("property") String property) {
        return  userService.getUsers(page, size, property);
    }

    @GetMapping(value = "/search", params = {"page", "size", "property", "searchString"})
    public ResponseEntity<UserResponse> getUsers(@RequestParam("page") int page,
                                                 @RequestParam("size") int size,
                                                 @RequestParam("property") String property,
                                                 @RequestParam("searchString") String searchString) {
        return  userService.searchUsers(page, size, property, searchString);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        return userService.deleteUser(id);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody User user) { return userService.updateUser(user); }

    @PutMapping("/unlock")
    public ResponseEntity<UserResponse> unlockUser(@RequestBody User user) { return userService.unlockUser(user); }

    @PutMapping("/password")
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

