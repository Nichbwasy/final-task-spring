package com.epam.services.conrollers;

import com.epam.models.Client;

public interface LoginService {
    Boolean loginExist(String login);
    Client loginClient(String login, String password);
}
