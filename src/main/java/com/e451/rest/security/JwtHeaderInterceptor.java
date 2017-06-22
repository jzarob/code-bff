package com.e451.rest.security;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by j747951 on 6/22/2017.
 */
public class JwtHeaderInterceptor implements ClientHttpRequestInterceptor {

    private final String headerName;

    public JwtHeaderInterceptor(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            JwtAuthToken jwtAuth = (JwtAuthToken ) authentication;
            httpRequest.getHeaders().set(headerName, jwtAuth.getToken());
        }
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
