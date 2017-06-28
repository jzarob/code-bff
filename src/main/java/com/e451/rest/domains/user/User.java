package com.e451.rest.domains.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by l659598 on 6/19/2017.
 */
@Document
@Component
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class User implements UserDetails {

    @Id
    private String id;
    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String password;
    private boolean enabled;

    @Indexed
    @JsonIgnore
    private String activationGuid;

    public User() {
    }

    public User(String id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = email;
        this.password = password;
        this.enabled = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationGuid() {
        return activationGuid;
    }

    public void setActivationGuid(String activationGuid) {
        this.activationGuid = activationGuid;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    //@Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    //@Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

   // @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


}
