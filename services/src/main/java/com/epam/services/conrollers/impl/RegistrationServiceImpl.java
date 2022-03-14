package com.epam.services.conrollers.impl;

import com.epam.models.Client;
import com.epam.models.Role;
import com.epam.repositories.RoleRepository;
import com.epam.repositories.ClientRepository;
import com.epam.services.conrollers.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    @Override
    public Client registerClient(String username, String password, String repeatPassword, String email, String firstName, String lastName) {
        if (!clientRepository.existsByUsername(username)) {
            if (!clientRepository.existsByEmail(email)) {
                Collection<Role> clientRoles = new ArrayList<>();
                clientRoles.add(roleRepository.getByName("CLIENT"));
                Client client = new Client(
                        username,
                        password,
                        email,
                        firstName,
                        lastName,
                        clientRoles
                );
                client.setEnabled(true);
                log.info("New client '{}' has been registered.", username);
                return clientRepository.save(client);
            } else log.warn("Email '{}' already exists!", email);
        } else log.warn("Username '{}' already exists!", email);
        return null;
    }
}
