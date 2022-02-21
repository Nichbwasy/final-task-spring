package com.epam.services.interfaces;

import com.epam.models.Client;

public interface ILoginService {
    Boolean loginExist(String login);
    Client loginClient(String login, String password);
}
