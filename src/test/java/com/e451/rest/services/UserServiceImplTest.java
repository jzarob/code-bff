package com.e451.rest.services;

import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.gateways.UserServiceGateway;
import com.e451.rest.services.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by l659598 on 6/20/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private UserService userService;

    @Mock
    private UserServiceGateway userServiceGateway;

    private List<User> users;

    @Before
    public void setup() {
        this.userService = new UserServiceImpl(userServiceGateway);

        users = Arrays.asList(
                new User("id1", "liz1", "conrad1", "email1", "password1"),
                new User("id2", "liz2", "conrad2", "email2", "password2"),
                new User("id3", "liz3", "conrad3", "email3", "password3")
        );
    }

    @Test
    public void whenCreateUser_returnNewUser() {
        UserResponse userResponse = new UserResponse();
        User user = users.get(0);
        userResponse.setUsers(Arrays.asList(user));

        ResponseEntity<UserResponse> gatewayRespone =
                ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

        when(userServiceGateway.createUser(user)).thenReturn(gatewayRespone);

        ResponseEntity<UserResponse> response = userService.createUser(user);

        Assert.assertEquals(1, response.getBody().getUsers().size());
    }
}
