package com.e451.rest.services.impl;

import com.e451.rest.domains.user.User;
import com.e451.rest.services.AccountLockoutService;
import com.e451.rest.services.FailedLoginService;
import com.e451.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by l659598 on 7/18/2017.
 */
@Service
public class AccountLockoutServiceImpl implements AccountLockoutService {

    @Autowired
    private UserService userService;

    @Autowired
    private FailedLoginService failedLoginService;

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

    }
}
