package com.epam.services.conrollers;

import com.epam.models.Client;
import com.epam.models.CreditCard;

public interface ClientService {
    Boolean usernameIsFree(String login);
    Boolean emailIsFree(String email);
    Client getClientByUsername(String username);
    CreditCard getClientCreditCard(Client client, String cardNumber);

}
