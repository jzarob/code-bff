package com.e451.rest.controllers;

import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by l659598 on 6/20/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UsersControllerTest {

    private UsersController controller;

    @Mock
    private UserService userService;

    private List<User> users;

    @Before
    public void setup() {
        controller = new UsersController(userService);
        users = Arrays.asList(
                new User("id1", "liz1", "conrad1", "email1", "password1"),
                new User("id2", "liz2", "conrad2", "email2", "password2"),
                new User("id3", "liz3", "conrad3", "email3", "password3")
        );
    }

    @Test
    public void whenGetUses_returnListOfUsers() {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(this.users);

        ResponseEntity<UserResponse> responseEntity =
                ResponseEntity.ok().body(userResponse);

        when(userService.getUsers()).thenReturn(responseEntity);

        ResponseEntity<UserResponse> response = controller.getUsers();

        Assert.assertEquals(users.size(), response.getBody().getUsers().size());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenCreateUser_returnNewUser() {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(Arrays.asList(users.get(0)));

        ResponseEntity<UserResponse> responseEntity =
                ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

        when(userService.createUser(users.get(0))).thenReturn(responseEntity);

        ResponseEntity<UserResponse> response = controller.createUser(users.get(0));

        Assert.assertEquals(users.get(0), response.getBody().getUsers().get(0));
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void whenActivate_returnResponseOK() {
        String uuid = UUID.randomUUID().toString();
        ResponseEntity responseEntity = ResponseEntity.ok().build();

        when(userService.activate(uuid)).thenReturn(responseEntity);

        ResponseEntity response = controller.activateUser(uuid.toString());

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
