package com.e451.rest.domains.auth;

import java.util.Date;

/**
 * Created by l659598 on 7/18/2017.
 */
public class FailedLoginAttempt {
    private String username;
    private String ipAddress;
    private Date date;

    public FailedLoginAttempt() {
    }

    public FailedLoginAttempt(String username, String ipAddress, Date date) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
