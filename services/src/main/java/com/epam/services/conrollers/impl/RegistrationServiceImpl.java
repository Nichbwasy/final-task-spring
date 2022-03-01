package com.epam.services.conrollers.impl;

import com.epam.models.Client;
import com.epam.models.Role;
import com.epam.repositories.RoleRepository;
import com.epam.repositories.ClientRepository;
import com.epam.services.conrollers.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    @Override
    public Client registerClient(Client client) {
        Collection<Role> clientRoles = new ArrayList<>();
        clientRoles.add(roleRepository.getByName("CLIENT"));
        client.setEnabled(true);
        client.setRoles(clientRoles);
        return clientRepository.save(client);
    }
}
