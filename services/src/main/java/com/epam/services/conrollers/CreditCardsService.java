package com.epam.services.conrollers;

import com.epam.models.Client;
import com.epam.models.CreditCard;

import java.util.List;

public interface CreditCardsService {
    Client getClientByUserName(String username);
    Boolean creditCardNumberIsFree(String cardNumber);
    Boolean addCreditCardToClient(Client user, CreditCard creditCard);
}
