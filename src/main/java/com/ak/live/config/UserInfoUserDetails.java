package com.ak.live.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ak.live.entities.User;

public class UserInfoUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    public UserInfoUserDetails(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.username = user.getEmailId();
        this.password = user.getPassword();
        this.authorities = Arrays.stream(user.getRoles().split(","))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim().toUpperCase()))
                .collect(Collectors.toList());
        this.isAccountNonExpired = true;  // Customize based on business logic
        this.isAccountNonLocked = true;   // Customize based on business logic
        this.isCredentialsNonExpired = true;
        this.isEnabled = user.isEnable(); // Assuming `isEnabled` exists in your `User` entity
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
