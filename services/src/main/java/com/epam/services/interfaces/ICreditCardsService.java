package com.epam.services.interfaces;

import com.epam.models.Client;
import com.epam.models.CreditCard;

import java.util.List;

public interface ICreditCardsService {
    Client refreshClient(Client client);
    List<CreditCard> getUpdatedClientCreditCards(Client client);
    Boolean addCreditCardToClient(Client client, String cardNumber, String expirationCardMonth, String expirationCardYear, String cvv);
}
