package com.epam.services.classes;

import com.epam.models.Balance;
import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.repositories.interfaces.IBalanceRepository;
import com.epam.repositories.interfaces.IClientRepository;
import com.epam.repositories.interfaces.ICreditCardRepository;
import com.epam.services.interfaces.ICreditCardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreditCardsService implements ICreditCardsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CreditCardsService.class);

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private ICreditCardRepository creditCardRepository;

    @Autowired
    private IBalanceRepository balanceRepository;

    @Override
    public Client refreshClient(Client client) {
        if (client != null) {
            client = clientRepository.findByLogin(client.getLogin());
            LOGGER.info("The client '{}' has been refreshed!", client);
            return client;
        } else {
            LOGGER.warn("Can't refresh a client 'case he is null!");
            return null;
        }
    }

    @Override
    public List<CreditCard> getUpdatedClientCreditCards(Client client) {
        Client updatedClient = clientRepository.findByLogin(client.getLogin());
        return updatedClient.getCreditCards();
    }

    @Override
    public Boolean addCreditCardToClient(Client client, String cardNumber, String expirationCardMonth, String expirationCardYear, String cvv) {
        Balance balance = new Balance(new BigDecimal(0));
        CreditCard creditCard = new CreditCard(client, cardNumber, expirationCardMonth, expirationCardYear, cvv, false, balance);
        creditCard = creditCardRepository.save(creditCard);
        if (creditCard != null) {
            LOGGER.info("New credit card '{}' for the client '{}' has been created.", creditCard, client);
            return true;
        } else {
            LOGGER.warn("Something went wrong while creation a credit card for the client '{}'", client);
            return false;
        }
    }
}
