package com.epam.services.conrollers;

import com.epam.models.Client;
import com.epam.models.CreditCard;

import java.math.BigDecimal;

public interface ReplenishmentService {
    Client getClientByUsername(String username);
    CreditCard getCreditCardByCardNumber(String cardNumber);
    Boolean balanceReplenishment(CreditCard creditCard, BigDecimal amount);
}
