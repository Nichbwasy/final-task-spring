package com.epam.services.interfaces;

import com.epam.models.Client;

public interface IRegistrationService {
    Client registerClient(String login, String password, String email, String firstName, String lastName);
}
