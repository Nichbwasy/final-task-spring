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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

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
    public List<Transaction> showTransactionHistory(Integer page) {
        return transactionsRepository.findAll(PageRequest.of(page, 5)).toList();
    }

    @Override
    public Transaction cancelTransaction(Long id) {
        if (transactionsRepository.existsById(id)) {
            Transaction transaction = transactionsRepository.getById(id);
            CreditCard senderCard = creditCardRepository.getByCardNumber(transaction.getSenderCardNumber());
            CreditCard recipientCard = creditCardRepository.getByCardNumber(transaction.getRecipientCardNumber());
            if (senderCard != null) {
                if (recipientCard != null) {
                    BigDecimal amount = transaction.getAmount();
                    transactionsRepository.delete(transaction);
                    return processTransaction(recipientCard, senderCard, amount);
                } else log.warn("Can't cancel transaction 'case recipient card '{}' not found!", transaction.getSenderCardNumber());
            } else log.warn("Can't cancel transaction 'case sender card '{}' not found!", transaction.getSenderCardNumber());
        } else log.warn("Transaction with id '{}' doesn't exist!", id);
        return null;
    }

    @Override
    public Transaction startTransaction(Client client, CreditCard senderCreditCard, String recipientCardNumber, String amount) {
        if (validateTransactionFormData(client, senderCreditCard, recipientCardNumber, amount)) {
            CreditCard senderCard = creditCardRepository.getByCardNumber(senderCreditCard.getCardNumber());
            CreditCard recipientCard = creditCardRepository.getByCardNumber(recipientCardNumber);
            BigDecimal value = new BigDecimal(amount);
            return processTransaction(senderCard, recipientCard, value);
        }
        return null;
    }

    @Transactional
    private Transaction processTransaction(CreditCard senderCard, CreditCard recipientCard, BigDecimal amount) {
        Balance senderBalance = senderCard.getBalance();
        Balance recipientBalance = recipientCard.getBalance();
        senderBalance.setAmount(senderBalance.getAmount().subtract(amount));
        recipientBalance.setAmount(recipientBalance.getAmount().add(amount));
        Transaction transaction = new Transaction(senderCard.getCardNumber(), recipientCard.getCardNumber(), amount);
        try {
            balanceRepository.save(senderBalance);
            balanceRepository.save(recipientBalance);
            transaction = transactionsRepository.save(transaction);
            log.info("The transaction between cards '{}' and '{}', on amount '{}' has been completed successfully"
                    , senderCard.getCardNumber(), recipientCard.getCardNumber(), amount);
            return transaction;
        } catch (Exception e) {
            log.error("Can't save balances! ", e);
        }
        return null;
    }

    private Boolean validateTransactionFormData(Client client, CreditCard senderCard, String recipientCardNumber, String amount) {
        if (client.getCreditCards().stream().anyMatch(card -> card.getCardNumber().equals(senderCard.getCardNumber()))) {
            if (creditCardRepository.existsByCardNumber(recipientCardNumber)) {
                CreditCard creditCard = creditCardRepository.getByCardNumber(senderCard.getCardNumber());
                if (creditCard.getCardExpirationMonth().equals(senderCard.getCardExpirationMonth())) {
                    if (creditCard.getCardExpirationYear().equals(senderCard.getCardExpirationYear())) {
                        if (creditCard.getCvv().equals(senderCard.getCvv())) {
                            if (creditCard.getBalance().getAmount().subtract(new BigDecimal(amount)).doubleValue() >= 0) {
                                return true;
                            } else log.warn("Not enough money!");
                        } else log.warn("Wrong CVV code!");
                    } else log.warn("Wrong expiration year!");
                } else log.warn("Wrong expiration month!");
            } else log.warn("The recipient credit card '{}' doesn't exist!", recipientCardNumber);
        } else log.warn("The credit card '{}' doesn't belong to the client '{}'.", senderCard.getCardNumber(), client.getUsername());
        return false;
    }
}
