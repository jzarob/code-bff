package com.e451.rest.services;

import com.e451.rest.domains.user.ResetForgottenPasswordRequest;
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
    ResponseEntity<UserResponse> getUsers();
    ResponseEntity<UserResponse> getUsers(int page, int size, String property);
    ResponseEntity<UserResponse> searchUsers(int page, int size, String property, String searchString);
    ResponseEntity<UserResponse> createUser(User user);
    ResponseEntity<UserResponse> unlockUser(User user);
    ResponseEntity<UserResponse> updateUser(User user);
    ResponseEntity<UserResponse> updateUser(UserVerification userVerification);
    ResponseEntity deleteUser(String id);
    ResponseEntity activate(String uuid);
    UserDetails loadUserByUsername(String username);
    ResponseEntity<UserResponse> getActiveUser();
    ResponseEntity forgotPassword(String username);
    ResponseEntity resetForgottenPassword(ResetForgottenPasswordRequest request);
}
