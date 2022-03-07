package com.epam.controllers.rest.replenishment;

import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.CreditCardsService;
import com.epam.services.conrollers.ReplenishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ReplenishmentController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditCardsService creditCardsService;

    @Autowired
    private ReplenishmentService replenishmentService;

    @GetMapping("/cards/replenishment")
    public Map<Long, CreditCard> replenishment(Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        return client.getCreditCards().stream().collect(Collectors.toMap(CreditCard::getId, Function.identity()));
    }

    @PostMapping("/cards/replenishment")
    public String replenishmentStart(@RequestParam MultiValueMap<String, String> formData, Principal principal)
    {
        Client client = clientService.getClientByUsername(principal.getName());
        String cardNumber = formData.getFirst("cardNumber");
        String cardExpirationMonth = formData.getFirst("cardExpirationMonth");
        String cardExpirationYear = formData.getFirst("cardExpirationYear");
        String cvv = formData.getFirst("cvv");
        String amount = formData.getFirst("amount");
        if (creditCardsService.creditCardBelongClientCheck(client, cardNumber)) {
            CreditCard creditCard = creditCardsService.getCreditCardByCardNumber(cardNumber);
            if (creditCard.getCardExpirationMonth().equals(cardExpirationMonth)){
                if (creditCard.getCardExpirationYear().equals(cardExpirationYear)) {
                    if (creditCard.getCvv().equals(cvv)) {
                        if (replenishmentService.balanceReplenishment(creditCard, new BigDecimal(amount))) {
                            log.info("Replenishment operation has been completed successfully!");
                            return "Successful replenishment";
                        } else {
                            log.warn("Replenishment operation wasn't completed!");
                        }
                    } else log.warn("Wrong CVV code!");
                } else log.warn("Wrong expiration year!");
            } else log.warn("Wrong expiration month!");
        } else log.warn("The credit card '{}' doesn't belong to the client '{}'.", cardNumber, client.getUsername());
        return "Unsuccessful replenishment";
    }
}
