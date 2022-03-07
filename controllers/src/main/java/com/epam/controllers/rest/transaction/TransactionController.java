package com.epam.controllers.rest.transaction;

import com.epam.models.Balance;
import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.CreditCardsService;
import com.epam.services.conrollers.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class TransactionController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditCardsService creditCardsService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/cards/transactions")
    public Map<Long, CreditCard> transaction(Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        return client.getCreditCards().stream().collect(Collectors.toMap(CreditCard::getId, Function.identity()));
    }

    @PostMapping("/cards/transactions")
    public String transactionStart(@RequestParam MultiValueMap<String, String> formData, Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        String senderCardNumber = formData.getFirst("senderCardNumber");
        String senderCardExpirationMonth = formData.getFirst("senderCardExpirationMonth");
        String senderCardExpirationYear = formData.getFirst("senderCardExpirationYear");
        String senderCvv = formData.getFirst("senderCvv");
        String amount = formData.getFirst("amount");
        String recipientCardNumber = formData.getFirst("recipientCardNumber");
        if (creditCardsService.creditCardBelongClientCheck(client, senderCardNumber)) {
            if (creditCardsService.creditCardAlreadyExist(recipientCardNumber)) {
                CreditCard creditCard = creditCardsService.getCreditCardByCardNumber(senderCardNumber);
                if (creditCard.getCardExpirationMonth().equals(senderCardExpirationMonth)) {
                    if (creditCard.getCardExpirationYear().equals(senderCardExpirationYear)) {
                        if (creditCard.getCvv().equals(senderCvv)) {
                            if (creditCard.getBalance().getAmount().subtract(new BigDecimal(amount)).doubleValue() >= 0) {
                                if (transactionService.startTransaction(senderCardNumber, recipientCardNumber, new BigDecimal(amount))){
                                    log.info("The transaction no amount '{}' between '{}' and '{}' credit cards completed successfully!"
                                            ,amount, senderCardExpirationMonth, recipientCardNumber);
                                    return "Successful transaction";
                                } else log.warn("Something went wrong while transfer money from the '{}' to the '{}' credit card!"
                                        ,senderCardNumber, recipientCardNumber);
                            } else log.warn("Not enough money!");
                        } else log.warn("Wrong CVV code!");
                    } else log.warn("Wrong expiration year!");
                } else log.warn("Wrong expiration month!");
            } else log.warn("The recipient credit card '{}' doesn't exist!", recipientCardNumber);
        } else log.warn("The credit card '{}' doesn't belong to the client '{}'.", senderCardNumber, client.getUsername());
        return "Unsuccessful transaction";
    }

}
