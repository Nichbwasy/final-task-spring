package com.epam.services.conrollers;

import java.math.BigDecimal;

public interface TransactionService {
    Boolean startTransaction(String senderCardNumber, String recipientCardNumber, BigDecimal amount);
}
