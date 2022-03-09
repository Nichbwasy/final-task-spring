package com.epam.controllers.rest.client.cards;

import com.epam.models.BlockingRequest;
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
import java.util.List;
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
    public List<CreditCard> cards(Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        return client.getCreditCards();
    }

    @GetMapping("/cards/{cardNumber}")
    public CreditCard card(@PathVariable String cardNumber, Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        return clientService.getClientCreditCard(client, cardNumber);
    }

    @PostMapping("/cards")
    public CreditCard save(@RequestBody CreditCard creditCard, Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        return creditCardsService.addCreditCardToClient(client, creditCard);
    }

    @PostMapping("/cards/block/{cardNumber}")
    public BlockingRequest blockCard(@PathVariable String cardNumber, Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        return blockingRequestService.sendRequest(client, cardNumber);
    }

    @DeleteMapping("/cards/{cardNumber}")
    public void delete(@PathVariable String cardNumber, Principal principal){
        Client client = clientService.getClientByUsername(principal.getName());
        creditCardsService.deleteCreditCardFromClient(client, cardNumber);
    }
}
