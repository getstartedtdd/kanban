package com.kanban.spring.security;

import com.kanban.core.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by L.x on 15-6-2.
 */
public interface UserDetailsDialect {
    UserDetails toUserDetails(User user);
}
