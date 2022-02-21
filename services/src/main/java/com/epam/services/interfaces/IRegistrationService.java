package com.epam.services.interfaces;

import com.epam.models.Client;

public interface IRegistrationService {
    Boolean loginIsFree(String login);
    Boolean emailIsFree(String email);
    Client registerClient(String login, String password, String email, String firstName, String lastName);
}
