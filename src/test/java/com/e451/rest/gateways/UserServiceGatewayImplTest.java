package com.e451.rest.gateways;

import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.domains.user.UserVerification;
import com.e451.rest.gateways.impl.UserServiceGatewayImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Arrays;
import java.util.UUID;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * Created by l659598 on 6/20/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceGatewayImplTest {

    @Mock
    private RestTemplate restTemplate;
    private UserServiceGateway userServiceGateway;

    private static final String BASE_URI = "fakeUri/users";
    private User user;

    @Before
    public void setup() {
        userServiceGateway = new UserServiceGatewayImpl("fakeUri", restTemplate);
        user = new User("id", "fname", "lname", "e@mail.com", "Password7");
    }

    @Test
    public void whenCreateUserCalled_thenRestTemplateIsCalled() throws Exception {
        UserResponse userResponse = new UserResponse();
        final User user =
                new User("id1", "liz", "conrad", "zil@darnoc.com", "passw0rd");
        userResponse.setUsers(Arrays.asList(user));
        ResponseEntity<UserResponse> response = ResponseEntity.ok(userResponse);

        when(restTemplate.postForEntity(BASE_URI, user, UserResponse.class)).thenReturn(response);

        userServiceGateway.createUser(user);

        verify(restTemplate).postForEntity(BASE_URI, user, UserResponse.class);
    }

    @Test
    public void whenActivateCalled_thenRestTemplateIsCalled() throws Exception {
        String uuid = UUID.randomUUID().toString();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URI)
                .pathSegment("activate", uuid.toString());

        when(restTemplate.getForEntity(builder.build().toUriString(), ResponseEntity.class))
                .thenReturn(ResponseEntity.ok().build());

        userServiceGateway.activate(uuid);

        verify(restTemplate).getForEntity(builder.build().toUriString(), ResponseEntity.class);
    }

    @Test
    public void whenUpdateUser_thenReturnUpdatedUser() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URI);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, null);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(Arrays.asList(user));

        when(restTemplate.exchange(builder.build().toUriString(), HttpMethod.PUT, requestEntity, UserResponse.class))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(userResponse));
        ResponseEntity<UserResponse> response = userServiceGateway.updateUser(user);

        Assert.assertEquals(user, response.getBody().getUsers().get(0));
        verify(restTemplate).exchange(builder.build().toUriString(), HttpMethod.PUT, requestEntity, UserResponse.class);
    }

    @Test
    public void whenUpdateUserVerification_thenReturnUpdatedUser() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URI);
        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setCurrentPassword("Password1!");
        HttpEntity<UserVerification> requestEntity = new HttpEntity<>(userVerification, null);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(Arrays.asList(user));

        when(restTemplate.exchange(builder.build().toUriString(), HttpMethod.PUT, requestEntity, UserResponse.class))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(userResponse));
        ResponseEntity<UserResponse> response = userServiceGateway.updateUser(userVerification);

        Assert.assertEquals(user, response.getBody().getUsers().get(0));
        verify(restTemplate).exchange(builder.build().toUriString(), HttpMethod.PUT, requestEntity, UserResponse.class);
    }

    @Test
    public void whenGetActiveUser_thenReturnActiveUser() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URI)
                .pathSegment("activeUser");
        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(Arrays.asList(user));

        when(restTemplate.getForEntity(builder.build().toUriString(), UserResponse.class))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(userResponse));

        userServiceGateway.getActiveUser();

        verify(restTemplate).getForEntity(builder.build().toUriString(), UserResponse.class);
    }

}
