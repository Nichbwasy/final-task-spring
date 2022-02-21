package com.epam.services.classes;

import com.epam.encrypting.Encryptor;
import com.epam.models.Client;
import com.epam.repositories.classes.ClientRepositoryImpl;
import com.epam.repositories.interfaces.IClientRepository;
import com.epam.roles.ClientRoles;
import com.epam.services.interfaces.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService implements IRegistrationService {

    @Autowired
    private IClientRepository clientRepository;

    @Override
    public Client registerClient(String login, String password, String email, String firstName, String lastName) {
        String encryptedPassword = Encryptor.encryptSHA256(password);
        Client client = new Client(login, encryptedPassword, email, firstName, lastName, ClientRoles.CLIENT);
        return clientRepository.save(client);
    }

    @Override
    public Boolean loginIsFree(String login) {
        return clientRepository.findByLogin(login) == null;
    }

    @Override
    public Boolean emailIsFree(String email) {
        return clientRepository.findByEmail(email) == null;
    }
}
