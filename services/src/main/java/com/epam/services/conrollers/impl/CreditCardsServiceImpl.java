package com.epam.services.conrollers.impl;

import com.epam.models.Balance;
import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.repositories.BalanceRepository;
import com.epam.repositories.UserRepository;
import com.epam.repositories.CreditCardRepository;
import com.epam.services.conrollers.CreditCardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreditCardsServiceImpl implements CreditCardsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CreditCardsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Override
    public Client refreshClient(Client user) {
        if (user != null) {
            user = userRepository.getByUsername(user.getUsername());
            LOGGER.info("The user '{}' has been refreshed!", user);
            return user;
        } else {
            LOGGER.warn("Can't refresh a user 'case he is null!");
            return null;
        }
    }

    @Override
    public List<CreditCard> getUpdatedClientCreditCards(Client client) {
        Client updatedClient = userRepository.getByUsername(client.getUsername());
        return updatedClient.getCreditCards();
    }

    @Override
    public Boolean addCreditCardToClient(Client user, String cardNumber, String expirationCardMonth, String expirationCardYear, String cvv) {
        Balance balance = new Balance(new BigDecimal(0));
        CreditCard creditCard = new CreditCard(user, cardNumber, expirationCardMonth, expirationCardYear, cvv, false, balance);
        creditCard = creditCardRepository.save(creditCard);
        if (creditCard != null) {
            LOGGER.info("New credit card '{}' for the user '{}' has been created.", creditCard, user);
            return true;
        } else {
            LOGGER.warn("Something went wrong while creation a credit card for the user '{}'", user);
            return false;
        }
    }
}
