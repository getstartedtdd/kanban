package com.kanban.spring.security;

import com.kanban.core.Role;
import com.kanban.core.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L.x on 15-6-2.
 */
public class SimpleUserDetailsDialect implements UserDetailsDialect {
    private List<GrantedAuthority> grant(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            authorities.add(grant(role));
        }
        return authorities;
    }

    private SimpleGrantedAuthority grant(Role role) {
        return grant(role.getName());
    }

    public static SimpleGrantedAuthority grant(String role) {
        return new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
    }

    @Override
    public UserDetails toUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grant(user.getRoles()));
    }
}
