package com.e451.rest.eventlisteners;

import com.e451.rest.domains.auth.FailedLoginAttempt;
import com.e451.rest.domains.user.User;
import com.e451.rest.repository.FailedLoginRepository;
import com.e451.rest.services.AccountLockoutService;
import com.e451.rest.services.FailedLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.io.InvalidClassException;
import java.util.Date;

/**
 * Created by l659598 on 7/18/2017.
 */
@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEventListener.class);

    private FailedLoginService failedLoginService;
    private AccountLockoutService accountLockoutService;

    @Autowired
    public AuthenticationEventListener(FailedLoginService failedLoginService, AccountLockoutService accountLockoutService) {
        this.failedLoginService = failedLoginService;
        this.accountLockoutService = accountLockoutService;
    }

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent authenticationEvent) {
        if (authenticationEvent instanceof InteractiveAuthenticationSuccessEvent || authenticationEvent instanceof AuthenticationSuccessEvent) {
            User user = (User) authenticationEvent.getAuthentication().getPrincipal();
            logger.info("Authentication success for user " + user.getUsername());
            accountLockoutService.processLoginSuccess(user.getUsername());
        } else if (authenticationEvent instanceof AbstractAuthenticationFailureEvent) {
            String ipAddress = "";
            String username = authenticationEvent.getAuthentication().getPrincipal().toString();
            //if (authenticationEvent.getAuthentication().getDetails() instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails auth = (WebAuthenticationDetails) authenticationEvent.getAuthentication().getDetails();
                ipAddress = auth.getRemoteAddress();
            //}

            logger.info("Authentication failed for user " + username + " at " + ipAddress);

            FailedLoginAttempt failedLoginAttempt = new FailedLoginAttempt(username, ipAddress, new Date());
            failedLoginService.createFailedLoginAttempt(failedLoginAttempt);

            accountLockoutService.processLoginFailure(username, ipAddress);
        }
    }

}
