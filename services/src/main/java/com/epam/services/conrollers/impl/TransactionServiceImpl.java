package com.epam.services.conrollers.impl;

import com.epam.models.Balance;
import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.models.Transaction;
import com.epam.repositories.BalanceRepository;
import com.epam.repositories.ClientRepository;
import com.epam.repositories.CreditCardRepository;
import com.epam.repositories.TransactionsRepository;
import com.epam.services.conrollers.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public Client getClientByUsername(String username) {
        return clientRepository.getByUsername(username);
    }

    @Override
    public CreditCard getCreditCardByCardNumber(String cardNumber) {
        return creditCardRepository.getByCardNumber(cardNumber);
    }

    @Override
    public Boolean creditCardAlreadyExist(String cardNumber) {
        return creditCardRepository.getByCardNumber(cardNumber) != null;
    }

    @Override
    @Transactional
    public Boolean startTransaction(String senderCardNumber, String recipientCardNumber, BigDecimal amount) {
        CreditCard senderCard = creditCardRepository.getByCardNumber(senderCardNumber);
        CreditCard recipientCard = creditCardRepository.getByCardNumber(recipientCardNumber);
        Balance senderBalance = senderCard.getBalance();
        Balance recipientBalance = recipientCard.getBalance();
        senderBalance.setAmount(senderBalance.getAmount().subtract(amount));
        recipientBalance.setAmount(recipientBalance.getAmount().add(amount));
        Transaction transaction = new Transaction(senderCardNumber, recipientCardNumber, amount);
        try {
            balanceRepository.save(senderBalance);
            balanceRepository.save(recipientBalance);
            transactionsRepository.save(transaction);
            log.info("The transaction between cards '{}' and '{}', on amount '{}' has been completed successfully", senderCardNumber, recipientCardNumber, amount);
            return true;
        } catch (Exception e) {
            log.error("Can't save balances! ", e);
        }
        return false;
    }
}
