package com.e451.rest.services;

import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * Created by l659598 on 6/20/2017.
 */
public interface UserService {
    ResponseEntity<UserResponse> createUser(User user);
    ResponseEntity activate(String uuid);
}
