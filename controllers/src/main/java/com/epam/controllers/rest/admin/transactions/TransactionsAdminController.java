package com.epam.controllers.rest.admin.transactions;

import com.epam.models.Transaction;
import com.epam.services.conrollers.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class TransactionsAdminController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions/history/{page}")
    public Map<Long, Transaction> transactions(@PathVariable Integer page) {
        return transactionService.showTransactionHistory(page - 1).stream()
                .collect(Collectors.toMap(Transaction::getId, Function.identity()));
    }

    @GetMapping("/transactions/history/undo/{id}")
    public void transactionsUndo(@PathVariable Long id) {
        transactionService.cancelTransaction(id);
    }
}
