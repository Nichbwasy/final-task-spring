package com.epam.services.conrollers;

import com.epam.models.Client;
import com.epam.models.CreditCard;

import java.math.BigDecimal;

public interface TransactionService {
    Client getClientByUsername(String username);
    CreditCard getCreditCardByCardNumber(String cardNumber);
    Boolean creditCardAlreadyExist(String cardNumber);
    Boolean startTransaction(String senderCardNumber, String recipientCardNumber, BigDecimal amount);
}
