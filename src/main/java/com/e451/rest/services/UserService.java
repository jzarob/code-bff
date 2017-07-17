package com.e451.rest.services;

import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.domains.user.UserVerification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

/**
 * Created by l659598 on 6/20/2017.
 */
public interface UserService {
    ResponseEntity<UserResponse> createUser(User user);
    ResponseEntity<UserResponse> updateUser(User user);
    ResponseEntity<UserResponse> updateUser(UserVerification userVerification);
    ResponseEntity activate(String uuid);
    UserDetails loadUserByUsername(String username);
    ResponseEntity<UserResponse> getActiveUser();
}
