package com.epam.services.classes;

import com.epam.encrypting.Encryptor;
import com.epam.models.Client;
import com.epam.repositories.interfaces.IClientRepository;
import com.epam.services.interfaces.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private IClientRepository clientRepository;

    @Override
    public Client loginClient(String login, String password) {
        String encryptedPassword = Encryptor.encryptSHA256(password);
        Client client = clientRepository.findByLogin(login);
        if (client.getPassword().trim().equals(encryptedPassword)) {
            LOGGER.info("Password for client with login '{}' is match.", login);
            return client;
        } else {
            LOGGER.info("Password for client with login '{}' doesn't match.", login);
            return null;
        }
    }

    @Override
    public Boolean loginExist(String login) {
        return clientRepository.findByLogin(login) != null;
    }
}
