package com.epam.services.conrollers;

import com.epam.models.Client;

public interface RegistrationService {
    Client registerClient(String username, String password, String repeatPassword, String email, String firstName, String lastName);
}
