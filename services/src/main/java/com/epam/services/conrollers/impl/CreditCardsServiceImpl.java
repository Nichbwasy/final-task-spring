package com.epam.services.conrollers.impl;

import com.epam.models.Balance;
import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.repositories.BalanceRepository;
import com.epam.repositories.ClientRepository;
import com.epam.repositories.CreditCardRepository;
import com.epam.services.conrollers.CreditCardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class CreditCardsServiceImpl implements CreditCardsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Override
    public Client getClientByUserName(String username) {
        return clientRepository.getByUsername(username);
    }

    @Override
    public Boolean creditCardNumberIsFree(String cardNumber) {
        return creditCardRepository.getByCardNumber(cardNumber) == null;
    }

    @Override
    public Boolean addCreditCardToClient(Client client, CreditCard creditCard) {
        Balance balance = new Balance(new BigDecimal(0));
        creditCard.setIsLocked(false);
        creditCard.setBalance(balance);
        creditCard.setClient(client);
        creditCard = creditCardRepository.save(creditCard);
        if (creditCard != null) {
            log.info("New credit card '{}' for the client '{}' has been created.", creditCard, client);
            return true;
        } else {
            log.warn("Something went wrong while creation a credit card for the client '{}'", client);
            return false;
        }
    }
}
