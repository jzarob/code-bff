package com.e451.rest.gateways;

import com.e451.rest.domains.user.ResetForgottenPasswordRequest;
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
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.zip.DataFormatException;

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

    private static final List<User> users = Arrays.asList(
            new User("id1", "liz", "conrad", "zil@darnoc.com", "passw0rd"),
            new User("id2", "liz", "conrad", "zil@darnoc.com", "passw0rd"),
            new User("id3", "liz", "conrad", "zil@darnoc.com", "passw0rd")
    );

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
    public  void whenGetUsersCalled_thenRestTemplateIsCalled() throws Exception {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(users);
        ResponseEntity<UserResponse> response = ResponseEntity.ok(userResponse);

        when(restTemplate.getForEntity(BASE_URI, UserResponse.class)).thenReturn(response);

        userServiceGateway.getUsers();

        verify(restTemplate).getForEntity(BASE_URI, UserResponse.class);
    }

    @Test
    public void whenGetUsersPageableCalled_thenRestTemplateIsCalled() throws Exception {
        UserResponse userResponse = new UserResponse();
        ResponseEntity<UserResponse> response = ResponseEntity.ok(userResponse);

        when(restTemplate.getForEntity("fakeUri/users?page=0&size=20&property=firstName", UserResponse.class)).thenReturn(response);

        userServiceGateway.getUsers(0,20,"firstName");

        verify(restTemplate).getForEntity("fakeUri/users?page=0&size=20&property=firstName", UserResponse.class);
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
    public void whenUnlockUser_thenVerifyRestTemplateCalled() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URI).pathSegment("unlock");
        HttpEntity<User> requestEntity = new HttpEntity<>(user, null);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(Arrays.asList(user));

        when(restTemplate.exchange(builder.build().toUriString(), HttpMethod.PUT, requestEntity, UserResponse.class))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(userResponse));
        ResponseEntity<UserResponse> response = userServiceGateway.unlockUser(user);

        Assert.assertEquals(user, response.getBody().getUsers().get(0));
        verify(restTemplate).exchange(builder.build().toUriString(), HttpMethod.PUT, requestEntity, UserResponse.class);
    }

    @Test
    public void whenUpdateUserVerification_thenVerifyRestTemplateCalled() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URI).pathSegment("password");
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

    @Test
    public void whenDeleteUserCalled_thenRestTempalteIsCalled() throws Exception {
        URI uri = new URI("fakeUri/users/1");

        HttpEntity request = new HttpEntity(null, null);

        when(restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());

        userServiceGateway.deleteUser("1");

        verify(restTemplate).exchange(uri, HttpMethod.DELETE, request, Object.class);
    }

    @Test
    public void whenSearchUsersCalled_thenRestTemplateIsCalled() throws Exception {
        UserResponse userResponse = new UserResponse();
        ResponseEntity<UserResponse> response = ResponseEntity.ok(userResponse);

        when(restTemplate.getForEntity("fakeUri/users/search?page=0&size=20&property=firstName&searchString=text", UserResponse.class)).thenReturn(response);

        userServiceGateway.searchUsers(0,20,"firstName", "text");

        verify(restTemplate).getForEntity("fakeUri/users/search?page=0&size=20&property=firstName&searchString=text", UserResponse.class);
    }

    @Test
    public void whenForgotPasswordCalled_thenRestTemplateIsCalled() throws  Exception {
        ResponseEntity response = ResponseEntity.ok().build();

        when(restTemplate.getForEntity("fakeUri/users/forgot-password?username=username", UserResponse.class)).thenReturn(response);

        userServiceGateway.forgotPassword("username");

        verify(restTemplate).getForEntity("fakeUri/users/forgot-password?username=username", ResponseEntity.class);
    }

    @Test
    public void whenResetForgottenPasswordCalled_thenRestTemplateIsCalled() throws Exception {
        ResetForgottenPasswordRequest request = new ResetForgottenPasswordRequest("username", "guid");
        HttpEntity<ResetForgottenPasswordRequest> requestEntity = new HttpEntity<>(request, null);
        ResponseEntity response = ResponseEntity.ok().build();

        when(restTemplate.exchange("fakeUri/users/forgot-password", HttpMethod.PUT, requestEntity, Object.class)).thenReturn(response);

        userServiceGateway.resetForgottenPassword(request);

        verify(restTemplate).exchange("fakeUri/users/forgot-password", HttpMethod.PUT, requestEntity, Object.class);
    }

    @Test
    public void whenResetForgottenPasswordCalled_gatewayThrowsHttpClientErrorException_returnsUnauthorized() {
        ResetForgottenPasswordRequest request = new ResetForgottenPasswordRequest("username", "guid");
        HttpEntity<ResetForgottenPasswordRequest> requestEntity = new HttpEntity<>(request, null);

        when(restTemplate.exchange("fakeUri/users/forgot-password", HttpMethod.PUT, requestEntity, Object.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        ResponseEntity response = userServiceGateway.resetForgottenPassword(request);

        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void whenResetForgttenPasswordCalled_gatewayThrowsException_returnsInternalServerError() {
        ResetForgottenPasswordRequest request = new ResetForgottenPasswordRequest("username", "guid");
        HttpEntity<ResetForgottenPasswordRequest> requestEntity = new HttpEntity<>(request, null);

        when(restTemplate.exchange("fakeUri/users/forgot-password", HttpMethod.PUT, requestEntity, Object.class))
            .thenThrow(new RecoverableDataAccessException("string"));

        ResponseEntity response = userServiceGateway.resetForgottenPassword(request);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
