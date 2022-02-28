package com.epam.services.conrollers;

import com.epam.models.Client;

public interface LoginService {
    Client loginClient(String login, String password);
}
