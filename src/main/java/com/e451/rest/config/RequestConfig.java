package com.e451.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by j747951 on 6/22/2017.
 */
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class RequestConfig {

    private String header;
    private HttpServletRequest request;

    @Autowired
    public  RequestConfig(@Value("${jwt.header}") String header, HttpServletRequest request) {
        this.header = header;
    }



}
