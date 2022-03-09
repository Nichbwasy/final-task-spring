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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;


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
    public List<Replenishment> showReplenishmentHistory(Integer page) {
        return replenishmentsRepository.findAll(PageRequest.of(page, 5)).toList();
    }

    @Override
    @Transactional
    public Replenishment balanceReplenishment(Client client, CreditCard creditCard, String amount) {
        if (validateFormData(client, creditCard)) {
            CreditCard replenishmentCreditCard = creditCardRepository.getByCardNumber(creditCard.getCardNumber());
            if (replenishmentCreditCard != null) {
                Balance balance = replenishmentCreditCard.getBalance();
                BigDecimal value = new BigDecimal(amount);
                balance.setAmount(balance.getAmount().add(value));
                replenishmentCreditCard.setBalance(balance);
                creditCardRepository.save(replenishmentCreditCard);
                Replenishment replenishment = new Replenishment(replenishmentCreditCard.getCardNumber(), value);
                replenishment = replenishmentsRepository.save(replenishment);
                return replenishment;
            } else {
                log.warn("Can't find credit card with number '{}'!", creditCard.getCardNumber());
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Boolean cancelOperation(Long id) {
        Replenishment operation = replenishmentsRepository.getById(id);
        CreditCard creditCard = creditCardRepository.getByCardNumber(operation.getCardNumber());
        if (creditCard != null) {
            Balance balance = creditCard.getBalance();
            balance.setAmount(balance.getAmount().subtract(operation.getAmount()));
            creditCard.setBalance(balance);
            creditCardRepository.save(creditCard);
            log.info("Replenishment operation on amount '{}' for the credit card '{}' was canceled."
                    , operation.getAmount(), operation.getCardNumber());
            replenishmentsRepository.delete(operation);
            log.info("Replenishment record with id '{}' was removed.", id);
            return true;
        } else {
            log.warn("Credit card '{}' not found!", operation.getCardNumber());
            return false;
        }
    }

    private Boolean validateFormData(Client client, CreditCard cardData) {
        if (client.getCreditCards().stream().anyMatch(card -> card.getCardNumber().equals(cardData.getCardNumber()))) {
            CreditCard creditCard = creditCardRepository.getByCardNumber(cardData.getCardNumber());
            if (creditCard.getCardExpirationMonth().equals(cardData.getCardExpirationMonth())){
                if (creditCard.getCardExpirationYear().equals(cardData.getCardExpirationYear())) {
                    if (creditCard.getCvv().equals(cardData.getCvv())) {
                        return true;
                    } else log.warn("Wrong CVV code!");
                } else log.warn("Wrong expiration year!");
            } else log.warn("Wrong expiration month!");
        } else log.warn("The credit card '{}' doesn't belong to the client '{}'.", cardData.getCardNumber(), client.getUsername());
        return false;
    }
}
