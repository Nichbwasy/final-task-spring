package com.epam.controllers.rest.cards;

import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.repositories.ClientRepository;
import com.epam.repositories.CreditCardRepository;
import com.epam.services.conrollers.BlockingRequestService;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.CreditCardsService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class CardsController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditCardsService creditCardsService;

    @Autowired
    private BlockingRequestService blockingRequestService;

    @GetMapping("/cards")
    public Map<Long, CreditCard> cards(Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        return client.getCreditCards().stream().collect(Collectors.toMap(CreditCard::getId, Function.identity()));
    }

    @GetMapping("/cards/{cardNumber}")
    public CreditCard card(@PathVariable String cardNumber, Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        if (creditCardsService.creditCardBelongClientCheck(client, cardNumber)) {
            return creditCardsService.getCreditCardByCardNumber(cardNumber);
        }
        return null;
    }

    @PostMapping("/cards")
    public Map<Long, CreditCard> save(@RequestBody CreditCard creditCard, Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        if (creditCardsService.addCreditCardToClient(client, creditCard)) {
            log.info("The credit card was added to the client successfully!");
        } else {
            log.warn("Something went wrong while adding the credit card!");
        }
        return cards(principal);
    }

    @PostMapping("/cards/block/{cardNumber}")
    public String blockCard(@PathVariable String cardNumber,Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        if (creditCardsService.creditCardBelongClientCheck(client, cardNumber)) {
            CreditCard creditCard = creditCardsService.getCreditCardByCardNumber(cardNumber);
            if (!blockingRequestService.requestAlreadyExist(cardNumber)) {
                blockingRequestService.sendRequest(cardNumber);
                if (creditCard.getIsLocked()) {
                    log.info("The request to unblock the credit card '{}' was sent.", cardNumber);
                    return "The request to unblock for the credit card '" + cardNumber + "' was sent.";
                } else {
                    log.info("The request to block for the credit card '{}' was sent.", cardNumber);
                    return "The request to block for the credit card '" + cardNumber + "' was sent.";
                }
            } else {
                log.warn("Blocking request already created!");
                return "Blocking request already created!";
            }
        } else {
            log.warn("The credit card doesn't '{}' belong to the client!", cardNumber);
            return "The credit card '" + cardNumber + "' doesn't belong to the client";
        }
    }

    @DeleteMapping("/cards/{cardNumber}")
    public Map<Long, CreditCard> delete(@PathVariable String cardNumber, Principal principal){
        Client client = clientService.getClientByUsername(principal.getName());
        if (creditCardsService.creditCardBelongClientCheck(client, cardNumber)) {
            if (creditCardsService.deleteCreditCardFromClient(client, cardNumber)) {
                log.info("The credit card '{}' has been deleted.", cardNumber);
            } else {
                log.warn("Can't delete the credit card '{}'", cardNumber);
            }
        } else {
            log.warn("The credit card '{}' doesn't belong to the client '{}'.", cardNumber, client.getUsername());
        }
        return cards(principal);
    }
}
