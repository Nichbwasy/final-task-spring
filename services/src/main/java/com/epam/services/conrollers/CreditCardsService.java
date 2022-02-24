package com.epam.services.conrollers;

import com.epam.models.Client;
import com.epam.models.CreditCard;

import java.util.List;

public interface CreditCardsService {
    Client refreshClient(Client user);
    List<CreditCard> getUpdatedClientCreditCards(Client user);
    Boolean addCreditCardToClient(Client user, String cardNumber, String expirationCardMonth, String expirationCardYear, String cvv);
}
