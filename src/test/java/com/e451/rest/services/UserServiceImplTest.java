package com.e451.rest.services;

import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.domains.user.UserVerification;
import com.e451.rest.gateways.UserServiceGateway;
import com.e451.rest.repository.UserRepository;
import com.e451.rest.services.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by l659598 on 6/20/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private UserService userService;

    @Mock
    private UserServiceGateway userServiceGateway;

    @Mock
    private UserRepository userRepository;

    private List<User> users;
    private UserResponse userResponse;
    private User user;

    @Before
    public void setup() {
        this.userService = new UserServiceImpl(userServiceGateway, userRepository);

        users = Arrays.asList(
                new User("id1", "liz1", "conrad1", "email1", "password1"),
                new User("id2", "liz2", "conrad2", "email2", "password2"),
                new User("id3", "liz3", "conrad3", "email3", "password3")
        );
        userResponse = new UserResponse();
        user = users.get(0);
        userResponse.setUsers(Arrays.asList(user));
    }

    @Test
    public void whenCreateUser_returnNewUser() {

        ResponseEntity<UserResponse> gatewayRespone =
                ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

        when(userServiceGateway.createUser(user)).thenReturn(gatewayRespone);

        ResponseEntity<UserResponse> response = userService.createUser(user);

        Assert.assertEquals(1, response.getBody().getUsers().size());
    }

    @Test
    public void whenGetUsers_returnListOfUsers() {
        UserResponse userResponse =  new UserResponse();
        userResponse.setUsers(users);

        ResponseEntity<UserResponse> gatewayResponse =
                ResponseEntity.status(HttpStatus.OK).body(userResponse);

        when(userServiceGateway.getUsers()).thenReturn(gatewayResponse);

        ResponseEntity<UserResponse> response = userService.getUsers();

        Assert.assertEquals(users.size(), response.getBody().getUsers().size());
    }

    @Test
    public void whenGetUsersPageable_returnListOfUsers() {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(this.users);
        userResponse.setPaginationTotalElements((long) this.users.size());

        ResponseEntity<UserResponse> gatewayResponse =
                new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);

        when(userServiceGateway.getUsers(0, 20, "title")).thenReturn(gatewayResponse);

        ResponseEntity<UserResponse> response = userService.getUsers(0, 20, "title");

        Assert.assertEquals(this.users.size(), response.getBody().getUsers().size());
        Assert.assertEquals(this.users.size(), (long) response.getBody().getPaginationTotalElements());
    }

    @Test
    public void whenActivateUser_returnOK() {
        String guid = UUID.randomUUID().toString();

        when(userServiceGateway.activate(guid)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity response = userService.activate(guid);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenloadUserByUserName_returnTheUserIfExists() {
        User user = users.get(0);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        User returnedUser = (User)userService.loadUserByUsername(user.getUsername());

        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(user, returnedUser);
    }

    @Test(expected = RecoverableDataAccessException.class)
    public void whenLoadUserByUserName_throwIfUserDoesNotExisit() {
        when(userRepository.findByUsername(any(String.class))).thenThrow(new RecoverableDataAccessException(("error")));

        userService.loadUserByUsername("test");
    }

    @Test
    public void whenUpdateUser_returnsUpdatedUsser() {
        when(userServiceGateway.updateUser(user)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(userResponse));

        ResponseEntity<UserResponse> response = userService.updateUser(user);

        Assert.assertEquals(user, response.getBody().getUsers().get(0));
    }

    @Test
    public void whenUpdateUserVerification_returnsUpdatedUser() {
        UserVerification userVerification = new UserVerification();
        userVerification.setCurrentPassword("Password1!");
        userVerification.setUser(user);
        when(userServiceGateway.updateUser(userVerification)).thenReturn(ResponseEntity.status(HttpStatus.OK)
            .body(userResponse));

        ResponseEntity<UserResponse> response = userService.updateUser(userVerification);

        Assert.assertEquals(user, response.getBody().getUsers().get(0));

    }

    @Test
    public void whenGetActiveUser_returnsActiveUser () {
        when(userServiceGateway.getActiveUser()).thenReturn(ResponseEntity.status(HttpStatus.OK).body(userResponse));

        ResponseEntity<UserResponse> response = userService.getActiveUser();

        Assert.assertEquals(user, response.getBody().getUsers().get(0));
    }

    public void whenDeleteUser_returnResponseEntity() {
        ResponseEntity gatewayResponse = new ResponseEntity(null, HttpStatus.NO_CONTENT);

        when(userServiceGateway.deleteUser("1")).thenReturn(gatewayResponse);

        ResponseEntity response = userService.deleteUser("1");

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
