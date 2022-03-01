package com.epam.services.conrollers;

import com.epam.models.Client;

public interface ClientService {
    Boolean usernameIsFree(String login);
    Boolean emailIsFree(String email);
    Client getClientByUsername(String username);
    Client getClientByEmail(String email);

}
