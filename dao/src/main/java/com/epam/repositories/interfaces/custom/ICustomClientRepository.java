package com.epam.repositories.interfaces.custom;

import com.epam.models.Client;

public interface ICustomClientRepository {
    Client findAllByLogin(String login);
}
