package com.epam.services.conrollers;

import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.models.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    Transaction startTransaction(Client client, CreditCard senderCard, String recipientCardNumber, String amount);
    Transaction cancelTransaction(Long id);
    List<Transaction> showTransactionHistory(Integer page);
}
