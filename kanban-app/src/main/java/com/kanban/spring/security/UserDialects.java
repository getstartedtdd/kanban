package com.kanban.spring.security;

import com.kanban.core.Role;
import com.kanban.core.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L.x on 15-6-2.
 */
public class UserDialects implements UserDetailsDialect, ClientDetailsDialect {
    public static List<GrantedAuthority> grant(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            authorities.add(grant(role));
        }
        return authorities;
    }

    public static SimpleGrantedAuthority grant(Role role) {
        return grant(role.getName());
    }

    public static SimpleGrantedAuthority grant(String role) {
        return new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
    }

    @Override
    public UserDetails toUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grant(user.getRoles()));
    }

    @Override
    public ClientDetails toClientDetails(final User user) {
        return new BaseClientDetails() {{
            setClientId(user.getClientId());
            setClientSecret(user.getClientSecret());
            setResourceIds(user.getResourceIds());
            setAuthorizedGrantTypes(user.getGrantedTypes());
            setScope(user.getScopes());
            setAuthorities(grant(user.getRoles()));
        }};
    }
}
