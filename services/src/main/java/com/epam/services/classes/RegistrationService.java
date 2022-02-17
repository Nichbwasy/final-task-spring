package com.epam.services.classes;

import com.epam.encrypting.Encryptor;
import com.epam.models.Client;
import com.epam.repositories.classes.ClientRepositoryImpl;
import com.epam.repositories.interfaces.IClientRepository;
import com.epam.roles.ClientRoles;
import com.epam.services.interfaces.IRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService implements IRegistrationService {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    private IClientRepository clientRepository;

    @Override
    public Client registerClient(String login, String password, String email, String firstName, String lastName) {
        String encryptedPassword = Encryptor.encryptSHA256(password);
        Client client = new Client(login, encryptedPassword, email, firstName, lastName, ClientRoles.CLIENT);
        return clientRepository.save(client);
    }
}
