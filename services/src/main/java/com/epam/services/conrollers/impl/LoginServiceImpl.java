package com.epam.services.conrollers.impl;

import com.epam.models.Client;
import com.epam.repositories.UserRepository;
import com.epam.services.conrollers.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserRepository clientRepository;

    @Override
    public Client loginClient(String login, String password) {
        Client user = clientRepository.getByUsername(login);
        if (user.getPassword().trim().equals(password)) {
            LOGGER.info("Password for user with login '{}' is match.", login);
            return user;
        } else {
            LOGGER.info("Password for user with login '{}' doesn't match.", login);
            return null;
        }
    }

    @Override
    public Boolean loginExist(String login) {
        return clientRepository.getByUsername(login) != null;
    }
}
