package com.e451.rest.services;

import com.e451.rest.domains.user.User;

/**
 * Created by l659598 on 7/18/2017.
 */
public interface AccountLockoutService {
    Boolean canAccountLogin(User user);
    void processLoginFailure(String username, String ipAddress);
}
