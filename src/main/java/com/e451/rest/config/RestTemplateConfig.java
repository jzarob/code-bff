package com.e451.rest.config;

import com.e451.rest.security.JwtHeaderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by j747951 on 6/22/2017.
 */
@Configuration
public class RestTemplateConfig {

    private String header;

    @Autowired
    public RestTemplateConfig(@Value("${jwt.header}") String header) {
        this.header = header;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();

        restTemplate.setInterceptors(
                Arrays.asList(
                        new JwtHeaderInterceptor(header)));
        return restTemplate;
    }
}
