package com.e451.rest.domains.user;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by e384873 on 7/17/2017.
 */
public class UserVerification {

    private User user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String currentPassword;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
