package com.e451.rest.services.impl;

import com.e451.rest.domains.auth.FailedLoginAttempt;
import com.e451.rest.domains.user.User;
import com.e451.rest.repository.UserRepository;
import com.e451.rest.services.AccountLockoutService;
import com.e451.rest.services.FailedLoginService;
import com.e451.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by l659598 on 7/18/2017.
 */
@Service
public class AccountLockoutServiceImpl implements AccountLockoutService {

    private UserRepository userRepository;

    private FailedLoginService failedLoginService;

    private Integer timeout;

    private Integer attemptLimit;

    @Autowired
    public AccountLockoutServiceImpl(
            UserRepository userRepository,
            FailedLoginService failedLoginService,
            @Value("${lockout.timeout}") Integer timeout,
            @Value("${lockout.attemptLimit}") Integer attemptLimit) {

        this.userRepository = userRepository;
        this.failedLoginService = failedLoginService;
        this.timeout = timeout;
        this.attemptLimit = attemptLimit;
    }

    @Override
    public Boolean canAccountLogin(User user) {
        if (!user.isAccountNonLocked()) {
            Date currentDate = new Date();
            Date timeoutDate = new Date(System.currentTimeMillis() - 1000 * timeout);
            String username = user.getUsername();
            int attempts =
                    failedLoginService.findByDateBetweenAndUsername(timeoutDate, currentDate, username).size();

            if(attempts == 0) {
               user.setLocked(false);
               user = userRepository.save(user);
            }

        }

        return user.isAccountNonLocked();
    }

    @Override
    public void processLoginFailure(String username, String ipAddress) {
        Date currentDate = new Date();
        Date timeoutDate = new Date(System.currentTimeMillis() - 1000 * timeout);

        int failedAttempts =
                failedLoginService.findByDateBetweenAndUsername(timeoutDate, currentDate, username).size();

        if (failedAttempts >= attemptLimit) {
            User user = userRepository.findByUsername(username);
            user.setLocked(true);
            userRepository.save(user);
        }
    }

}
