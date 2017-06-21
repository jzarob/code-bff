package com.e451.rest.services.impl;

import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.gateways.UserServiceGateway;
import com.e451.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by l659598 on 6/20/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserServiceGateway userServiceGateway;

    @Autowired
    public UserServiceImpl(UserServiceGateway userServiceGateway) {
        this.userServiceGateway = userServiceGateway;
    }

    @Override
    public ResponseEntity<UserResponse> createUser(User user) {
        return userServiceGateway.createUser(user);
    }

    @Override
    public ResponseEntity activate(String guid) {
        return userServiceGateway.activate(guid);
    }

}
