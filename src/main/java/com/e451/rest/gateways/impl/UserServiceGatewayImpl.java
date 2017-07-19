package com.e451.rest.gateways.impl;

import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.domains.user.UserVerification;
import com.e451.rest.gateways.UserServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

/**
 * Created by l659598 on 6/20/2017.
 */
@Service
public class UserServiceGatewayImpl implements UserServiceGateway {

    private final String userServiceUri;
    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceGatewayImpl(@Value("${service-uri}") String userServiceUri,
                                  RestTemplate restTemplate) {
        this.userServiceUri = userServiceUri + "/users";
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<UserResponse> getUsers() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUri);
        ResponseEntity response;

        try {
            response = restTemplate.getForEntity(builder.build().toUriString(), UserResponse.class);
            return ResponseEntity.ok((UserResponse) response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<UserResponse> getUsers(int page, int size, String property) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUri)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("property", property);
        ResponseEntity response;

        try {
            response = restTemplate.getForEntity(builder.build().toUriString(), UserResponse.class);
            return ResponseEntity.ok((UserResponse) response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity deleteUser(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUri).pathSegment(id);
        HttpEntity requestEntity = new HttpEntity(null, null);

        try {
            restTemplate.exchange(builder.build().toUri(), HttpMethod.DELETE, requestEntity, Object.class);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<UserResponse> createUser(User user) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUri);
        ResponseEntity response;

        try {
            response = restTemplate.postForEntity(builder.build().toUriString(), user, UserResponse.class);
            return ResponseEntity.status(HttpStatus.CREATED).body((UserResponse) response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<UserResponse> unlockUser(User user) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUri).pathSegment("unlock");
        ResponseEntity response;
        HttpEntity<User> requestEntity = new HttpEntity<>(user, null);

        try {
            response = restTemplate.exchange(builder.build().toUriString(), HttpMethod.PUT, requestEntity, UserResponse.class);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body((UserResponse) response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(User user) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUri);
        ResponseEntity response;
        HttpEntity<User> requestEntity = new HttpEntity<>(user, null);

        try {
            response = restTemplate.exchange(builder.build().toUriString(), HttpMethod.PUT, requestEntity, UserResponse.class);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body((UserResponse) response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(UserVerification userVerification) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUri).pathSegment("password");
        ResponseEntity response;
        HttpEntity<UserVerification> requestEntity = new HttpEntity<>(userVerification, null);
        try {
            response = restTemplate.exchange(builder.build().toUriString(), HttpMethod.PUT, requestEntity, UserResponse.class);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body((UserResponse) response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    @Override
    public ResponseEntity activate(String uuid) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUri).pathSegment("activate", uuid);
        return restTemplate.getForEntity(builder.build().toUriString(), ResponseEntity.class);
    }

    @Override
    public ResponseEntity<UserResponse> getActiveUser() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUri).pathSegment("activeUser");
        ResponseEntity response;
        try {
            response = restTemplate.getForEntity(builder.build().toUriString(), UserResponse.class);
            return ResponseEntity.status(HttpStatus.OK).body((UserResponse) response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
