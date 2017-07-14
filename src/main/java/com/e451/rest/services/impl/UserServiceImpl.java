package com.e451.rest.services.impl;

import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.gateways.UserServiceGateway;
import com.e451.rest.repository.UserRepository;
import com.e451.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by l659598 on 6/20/2017.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserServiceGateway userServiceGateway;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserServiceGateway userServiceGateway, UserRepository userRepository) {
        this.userServiceGateway = userServiceGateway;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<UserResponse> getUsers() {
        return userServiceGateway.getUsers();
    }

    @Override
    public ResponseEntity<UserResponse> createUser(User user) {
        return userServiceGateway.createUser(user);
    }

    @Override
    public ResponseEntity deleteUser(String id) { return userServiceGateway.deleteUser(id); }

    @Override
    public ResponseEntity activate(String guid) {
        return userServiceGateway.activate(guid);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username %s" ,username));
        }

        return user;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
