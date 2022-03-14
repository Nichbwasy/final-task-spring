package com.epam.controllers.rest.client.cards.replenishment;

import com.epam.dto.cards.ReplenishmentFormDto;
import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.models.Replenishment;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.CreditCardsService;
import com.epam.services.conrollers.ReplenishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/cards/replenishment")
    public ResponseEntity<Replenishment> replenishmentStart(@RequestBody ReplenishmentFormDto formData, Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        return ResponseEntity.ok()
                .body(replenishmentService.balanceReplenishment(client, formData.getCreditCard(), formData.getAmount()));
    }
}
