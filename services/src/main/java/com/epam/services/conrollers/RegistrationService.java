package com.epam.services.conrollers;

import com.epam.models.Client;

public interface RegistrationService {
    Boolean usernameIsFree(String login);
    Boolean emailIsFree(String email);
    Client registerClient(Client client);
}
