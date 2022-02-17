package com.epam.services.interfaces;

import com.epam.models.Client;

public interface ILoginService {
    Client loginClient(String login, String password);
}
