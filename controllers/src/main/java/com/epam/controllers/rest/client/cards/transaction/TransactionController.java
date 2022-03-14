package com.epam.controllers.rest.client.cards.transaction;

import com.epam.dto.cards.TransactionFormDto;
import com.epam.models.Client;
import com.epam.models.Transaction;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.CreditCardsService;
import com.epam.services.conrollers.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
public class TransactionController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditCardsService creditCardsService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/cards/transactions")
    public ResponseEntity<Transaction> transactionStart(@RequestBody TransactionFormDto transactionForm, Principal principal) {
        Client client = clientService.getClientByUsername(principal.getName());
        return ResponseEntity.ok().body(transactionService.startTransaction(client, transactionForm.getSenderCard(),
                transactionForm.getRecipientCardNumber(), transactionForm.getAmount()));
    }

}
