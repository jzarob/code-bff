package com.e451.rest.eventlisteners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Created by l659598 on 7/18/2017.
 */
@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEventListener.class);

    public AuthenticationEventListener() {

    }

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent authenticationEvent) {
        if (authenticationEvent instanceof InteractiveAuthenticationSuccessEvent || authenticationEvent instanceof AuthenticationSuccessEvent) {
            logger.info("Authentication success.");

        } else if (authenticationEvent instanceof AbstractAuthenticationFailureEvent) {
            logger.info("Authentication failure.");
            //Add failed attempt to DB
            //AccountLockoutService.processAttempt ...
        }
    }

}
