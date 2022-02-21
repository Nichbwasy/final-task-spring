package com.epam.repositories.interfaces.custom;

import com.epam.models.Client;

public interface ICustomClientRepository {
    Client findByLogin(String login);
    Client findByEmail(String email);
}
