package com.e451.rest.services.impl;

import com.e451.rest.domains.auth.FailedLoginAttempt;
import com.e451.rest.domains.user.User;
import com.e451.rest.services.AccountLockoutService;
import com.e451.rest.services.FailedLoginService;
import com.e451.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by l659598 on 7/18/2017.
 */
@Service
public class AccountLockoutServiceImpl implements AccountLockoutService {

    @Autowired
    private UserService userService;

    @Autowired
    private FailedLoginService failedLoginService;

    @Value("${lockout.timeout}")
    private Integer timeout;

    @Value("${lockout.attemptLimit}")
    private Integer attemptLimit;

    @Autowired
    public AccountLockoutServiceImpl(UserService userService, FailedLoginService failedLoginService) {
        this.userService = userService;
        this.failedLoginService = failedLoginService;
    }
    @Override
    public Boolean canAccountLogin(User user) {
        return null;
    }

    @Override
    public void processLoginFailure(String username, String ipAddress) {
        Date currentDate = new Date();
        Date timeoutDate = new Date(System.currentTimeMillis() - 1000 * timeout);

        int failedAttempts =
                failedLoginService.findByDateBetweenAndUsername(timeoutDate, currentDate, username).size();

        if (failedAttempts >= attemptLimit) {
            User user = userService.loadUserByUsername(username);
            user.setLocked(false);
            userService.update(user);
        }
    }
}
