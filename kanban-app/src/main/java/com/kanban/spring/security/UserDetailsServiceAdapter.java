package com.kanban.spring.security;

import com.kanban.core.User;
import com.kanban.core.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by L.x on 15-6-2.
 */
public class UserDetailsServiceAdapter implements UserDetailsService {
    private UserRepository userRepository;
    private UserDetailsDialect userDetailsDialect;

    public UserDetailsServiceAdapter(UserRepository userRepository, UserDetailsDialect userDetailsDialect) {
        this.userRepository = userRepository;
        this.userDetailsDialect = userDetailsDialect;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found:" + username);
        }
        return userDetailsDialect.toUserDetails(user);
    }
}
