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
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    public CreditCard getCreditCardByCardNumber(String cardNumber) {
        return creditCardRepository.getByCardNumber(cardNumber);
    }

    @Override
    public Boolean creditCardAlreadyExist(String cardNumber) {
        return creditCardRepository.getByCardNumber(cardNumber) != null;
    }

    @Override
    @Transactional
    public Boolean creditCardBelongClientCheck(Client client, String cardNumber) {
        List<CreditCard> clientCards = creditCardRepository.findByClient(client);
        return clientCards.stream().anyMatch(card -> card.getCardNumber().equals(cardNumber));
    }

    @Override
    @Transactional
    public Boolean deleteCreditCardFromClient(Client client, String cardNumber) {
        CreditCard creditCardToDelete = creditCardRepository.getByCardNumber(cardNumber);
        if (creditCardToDelete != null) {
            creditCardRepository.deleteByCardNumber(cardNumber);
            log.info("The credit card '{}' of the client '{}' has been removed!", cardNumber, client);
            return true;
        }
        log.warn("The credit card '{}' not found!", cardNumber);
        return false;
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
