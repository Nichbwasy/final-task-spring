package com.epam.services.conrollers.impl;

import com.epam.models.Balance;
import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.models.Replenishment;
import com.epam.repositories.BalanceRepository;
import com.epam.repositories.ClientRepository;
import com.epam.repositories.CreditCardRepository;
import com.epam.repositories.ReplenishmentsRepository;
import com.epam.services.conrollers.ReplenishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;


@Slf4j
@Service
public class ReplenishmentServicesImpl implements ReplenishmentService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private ReplenishmentsRepository replenishmentsRepository;

    @Override
    public Client getClientByUsername(String username) {
        return clientRepository.getByUsername(username);
    }

    @Override
    public CreditCard getCreditCardByCardNumber(String cardNumber) {
        return creditCardRepository.getByCardNumber(cardNumber);
    }

    @Override
    @Transactional
    public Boolean balanceReplenishment(CreditCard creditCard, BigDecimal amount) {
        CreditCard replenishmentCreditCard = creditCardRepository.getByCardNumber(creditCard.getCardNumber());
        if (replenishmentCreditCard != null) {
            Balance balance = replenishmentCreditCard.getBalance();
            balance.setAmount(balance.getAmount().add(amount));
            replenishmentCreditCard.setBalance(balance);
            creditCardRepository.save(replenishmentCreditCard);
            Replenishment replenishment = new Replenishment(replenishmentCreditCard.getCardNumber(), amount);
            replenishmentsRepository.save(replenishment);
            return true;
        } else {
            log.warn("Can't find credit card with number '{}'!", creditCard.getCardNumber());
            return false;
        }
    }
}
