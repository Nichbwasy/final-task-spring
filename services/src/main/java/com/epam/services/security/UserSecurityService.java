package com.epam.services.security;

import com.epam.models.Client;
import com.epam.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private ClientRepository userRepository;

    public Client findUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.getByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = findUserByUsername(username);
        if (client == null) {
            throw new UsernameNotFoundException(String.format("Client with username '%s' not found!", username));
        }
        return new User(client.getUsername(), client.getPassword(), getAuthority(client));
    }

    private Set<SimpleGrantedAuthority> getAuthority(Client client) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        client.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }
}
