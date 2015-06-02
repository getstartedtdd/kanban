package com.kanban.spring.security;

import com.kanban.core.User;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * Created by L.x on 15-6-3.
 */
public interface ClientDetailsDialect {
    ClientDetails toClientDetails(User user);
}
