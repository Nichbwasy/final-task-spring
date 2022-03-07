package com.epam.services.conrollers;

import com.epam.models.Client;
import com.epam.models.CreditCard;

import java.util.List;

public interface CreditCardsService {
    Boolean creditCardAlreadyExist(String cardNumber);
    Boolean addCreditCardToClient(Client user, CreditCard creditCard);
    Boolean creditCardBelongClientCheck(Client client, String cardNumber);
    Boolean deleteCreditCardFromClient(Client client, String cardNumber);
    CreditCard getCreditCardByCardNumber(String cardNumber);

}
