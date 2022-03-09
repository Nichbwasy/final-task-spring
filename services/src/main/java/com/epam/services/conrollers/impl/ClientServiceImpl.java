package com.epam.services.conrollers.impl;

import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.repositories.ClientRepository;
import com.epam.repositories.CreditCardRepository;
import com.epam.services.conrollers.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public Boolean usernameIsFree(String login) {
        return !clientRepository.existsByUsername(login);
    }

    @Override
    public Boolean emailIsFree(String email) {
        return !clientRepository.existsByEmail(email);
    }

    @Override
    public Client getClientByUsername(String username) {
        return clientRepository.getByUsername(username);
    }

    @Override
    public CreditCard getClientCreditCard(Client client, String cardNumber) {
        if (client.getCreditCards().stream().anyMatch(card -> card.getCardNumber().equals(cardNumber))) {
            log.info("Returns credit card '{}' to client.", cardNumber);
            return creditCardRepository.getByCardNumber(cardNumber);
        } else {
            log.warn("Credit card '{}' doesn't belong to client!", cardNumber);
            return null;
        }
    }
}
