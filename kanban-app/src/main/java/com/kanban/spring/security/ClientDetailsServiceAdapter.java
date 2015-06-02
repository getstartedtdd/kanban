package com.kanban.spring.security;

import com.kanban.core.User;
import com.kanban.core.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;

/**
 * Created by L.x on 15-6-2.
 */
public class ClientDetailsServiceAdapter implements ClientDetailsService {

    private final UserRepository userRepository;
    private final ClientDetailsDialect clientDetailsDialect;

    public ClientDetailsServiceAdapter(UserRepository userRepository, ClientDetailsDialect clientDetailsDialect) {
        this.userRepository = userRepository;
        this.clientDetailsDialect = clientDetailsDialect;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        User user = userRepository.findUserByClientId(clientId);
        if (user == null) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        return clientDetailsDialect.toClientDetails(user);
    }
}
